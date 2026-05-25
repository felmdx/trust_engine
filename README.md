# TrustEngine - Motor de Análise de Risco e Prevenção à Fraude

O **TrustEngine** é um microsserviço de alta performance focado em segurança defensiva e análise de risco para autenticação bancária. O objetivo principal deste motor é avaliar tentativas de login em tempo real, mitigar ataques cibernéticos de alta concorrência (como *Brute Force* e *Credential Stuffing*) e gerenciar o vínculo de dispositivos confiáveis utilizando conceitos de **Arquitetura Limpa (Clean Architecture)**.

Este projeto foi desenhado com base nos padrões atuais de engenharia de software para refletir cenários reais de grandes corporações e fintechs focando em performance, manutenibilidade.

---

## 🎯 Problema de Negócio Solucionado

Tendo em vista que atacantes utilizam bots para testar milhões de credenciais vazadas por minuto (*Credential Stuffing*) ou tentar adivinhar senhas (*Brute Force*), se faz necessário adicionar camadas de defesa além da própria senha.
O **TrustEngine** atua como uma **camada de defesa inteligente** entre o usuário e o core bancário, decidindo de forma autônoma se o acesso deve ser:
1. **PERMITIDO:** Baixo risco (Aparelho conhecido e pertencente ao usuário).
2. **DESAFIADO (EXIGIR_MFA):** Risco Médio (Novo dispositivo ou divergência de dados).
3. **BLOQUEADO (Rate Limit):** Comportamento abusivo de tráfego detectado na porta de entrada.

---

## Arquitetura

O projeto adota os princípios da **Clean Architecture (Arquitetura Hexagonal / Ports & Adapters)**. A grande vantagem estratégica dessa abordagem em sistemas corporativos é o **desacoplamento total das regras de negócio em relação a frameworks e bancos de dados**.

```text
trust-engine
└── src/main/java/com/trustengine/trust_engine
    ├── application/               # Orquestra as coisas (Casos de Uso e DTOs)
    │   ├── dtos
    │   └── usecases
    ├── domain/                    # O coração do negócio (Puro Java, sem Spring!)
    │   ├── entities               # Nossas classes ricas (Dispositivo, Login)
    │   ├── enums
    │   ├── exceptions
    │   ├── policies               # Regras específicas
    │   └── repositories           # Apenas interfaces (Contratos)
    ├── infrastructure/            # Comunicação externa, Banco de Dados e Segurança
    │   ├── persistence
    │   │   ├── postgres           # Implementação do JPA e PostgreSQL
    │   │   └── redis              # Adaptador do NoSQL
    │   └── security               # Nossos filtros de defesa (AppSec)
    └── presentation/              # A porta de entrada da API (REST)
        ├── controllers
        │   ├── advice             # Tratamento global de erros
        │   ├── AuthController.java
        │   └── DeviceController.java
        └── payloads               # Classes que mapeiam os JSONs (Requests/Responses)
```

### Decisões de Design Importantes:
* **Domínio Rico:** As entidades de negócio possuem regras próprias de validação e estado, prevenindo o modelo anêmico.
* **Mappers Isolados:** Entidades de Domínio nunca são expostas na API e nunca viram tabelas diretamente. Existem objetos específicos para transporte de dados (DTOs), persistência relacional (`JpaEntity`) e o domínio. Mappers fazem a tradução nas fronteiras das camadas.
* **Ports & Adapters:** Se amanhã a empresa decidir trocar o PostgreSQL pelo MongoDB ou AWS DynamoDB, a camada `domain` e `application` permanecem 100% intocadas. Altera-se apenas o `Adapter` na infraestrutura.

---

## Tecnologias e Infraestrutur

* **Java 21 LTS** (Uso de recursos modernos e performance estável)
* **Spring Boot 3.x** (Core do ecossistema e gerenciamento de inversão de controle)
* **PostgreSQL** (Banco de dados relacional para persistência de dados de alta integridade)
* **Redis (Alpine OS)** (Banco NoSQL em memória utilizado como barreira ultra rápida)
* **RabbitMQ** (Mensageria assíncrona para disparo de eventos desacoplados, como envio de MFA)
* **Docker & Docker Compose** (Orquestração local e padronização de ambientes de desenvolvimento)
* **Maven** (Gerenciador de dependências e automação de builds)

---

## Mecanismos Implementados

Defesa contra Força Bruta (Rate Limiting): Criei um RateLimitingFilter com Redis. Se um IP disparar mais de 5 logins em 1 minuto, ele toma bloqueio (Too Many Requests) antes mesmo de bater no banco.

Evitando Falhas de Controle de Acesso (Broken Access Control): O sistema não olha só se o celular é confiável, mas checa se aquele aparelho pertence exatamente ao usuário que está logando, prevenindo roubo de sessões.

Mensageria Assíncrona (Event-Driven): Uso do RabbitMQ para terceirizar processos lentos. Quando o motor exige MFA, ele não bloqueia a resposta. A decisão HTTP é devolvida instantaneamente e um evento MfaSolicitadoEvent é jogado na fila para que um Worker em segundo plano processe (simulando envio de SMS).

Segredos Seguros: Utilização de variáveis de ambiente (${DB_PASS:senha_local}) para o código rodar liso na minha máquina, mas seguro quando for para produção/nuvem.---

## Fluxo de Execução de Teste da API

### Pré-requisitos
Certifique-se de ter o Docker e o Java 21 instalados. No terminal, suba a infraestrutura local:
```bash
docker compose up -d
.\mvnw.cmd spring-boot:run
```
### Teste Ponta a Ponta

#### Passo 1: O Primeiro Login (Aparelho Desconhecido)
Vamos simular um usuário logando pela primeira vez. Como o banco está vazio, o motor vai barrar.

```
POST http://localhost:8080/api/v1/auth/analyze-login
```
Body (JSON):

```JSON
{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "ipAddress": "192.168.1.50",
  "userAgent": "Mozilla/5.0 (iPhone; CPU iPhone OS 16_0 like Mac OS X)",
  "deviceFingerprint": "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
  "isPasswordValid": true
}
```
```
Resultado: O motor deve retornar a ação EXIGIR_MFA.
```

#### Passo 2: Vinculando o Aparelho (Simulando o MFA correto)
O usuário digitou o SMS no app, então avisamos nossa API para salvar esse celular como confiável.

```
POST http://localhost:8080/api/v1/devices
```
Body (JSON):

```JSON
{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "deviceFingerprint": "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
  "modelo": "iPhone 13 - iOS 16"
}
```

```
Resultado: Status 201 Created. (Salvou no Postgres)
```

#### Passo 3: O Segundo Login (Agora ele entra!)
O usuário tenta logar de novo no dia seguinte com o mesmo celular.
```
POST http://localhost:8080/api/v1/auth/analyze-login
```
Envie exatamente o mesmo JSON do Passo 1.

Resultado: Agora o motor acha o celular no banco, vê que é do usuário e retorna PERMITIR com risco BAIXO.

#### Passo 4: O Teste do Escudo (Rate Limit)
Fique clicando no botão de Send do login repetidas vezes bem rápido.

Na 6ª vez, o terminal vai avisar do ataque e o Postman vai receber um Erro 429 bloqueando o seu IP.
