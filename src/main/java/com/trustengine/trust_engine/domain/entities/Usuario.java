package com.trustengine.trust_engine.domain.entities;

import lombok.Getter; //Omite getters
import java.time.LocalDateTime;
import java.util.UUID;
import com.trustengine.trust_engine.domain.enums.UsuarioStatus;

@Getter
public class Usuario {
    private final UUID id;
    private final String email;
    private UsuarioStatus status;
    private final LocalDateTime dataCriacao;

    public Usuario(String email) {
        validateEmail(email);
        this.id = UUID.randomUUID();
        this.email = email;
        this.status = UsuarioStatus.ATIVO;
        this.dataCriacao = LocalDateTime.now();
    }

    // Construtor para usuário que já vem do banco de dados
    public Usuario(UUID id, String email, UsuarioStatus status, LocalDateTime dataCriacao) {
        this.id = id;
        this.email = email;
        this.status = status;
        this.dataCriacao = dataCriacao;
    }

    public void bloquear() {
        this.status = UsuarioStatus.BLOQUEADO_PREVENTIVAMENTE;
    }

    public void ativar() {
        this.status = UsuarioStatus.ATIVO;
    }

    private void validateEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido para a identidade do usuário.");
        }
    }
}