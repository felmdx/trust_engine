package com.trustengine.trust_engine.application.usecases;

import com.trustengine.trust_engine.domain.entities.Dispositivo;
import com.trustengine.trust_engine.domain.enums.AcaoRecomendada;
import com.trustengine.trust_engine.domain.enums.RiscoCalculado;
import com.trustengine.trust_engine.domain.repositories.DispositivoRepository;
import com.trustengine.trust_engine.presentation.payloads.requests.AnaliseLoginRequest;
import com.trustengine.trust_engine.presentation.payloads.responses.AnaliseLoginResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor // Importante: O Lombok vai injetar o seu DispositivoRepositoryImpl aqui automaticamente!
public class AnaliseLoginUseCase {

    // A Porta de entrada para o banco de dados
    private final DispositivoRepository dispositivoRepository;

    public AnaliseLoginResponse execute(AnaliseLoginRequest request) {
        
        if (!request.getIsPasswordValid()) {
            return AnaliseLoginResponse.builder()
                .acao(AcaoRecomendada.NEGAR)
                .reason("CREDENCIAIS_INVALIDAS")
                .riskScore(RiscoCalculado.ALTO)
                .build();
        }

        Optional<Dispositivo> dispositivoOpt = dispositivoRepository.procuraPorFingerprint(request.getDeviceFingerprint());

        if (dispositivoOpt.isEmpty() || !dispositivoOpt.get().isConfiavel()) {
            return AnaliseLoginResponse.builder()
                .acao(AcaoRecomendada.EXIGIR_MFA)
                .reason("DISPOSITIVO_NAO_RECONHECIDO_OU_REVOGADO")
                .riskScore(RiscoCalculado.MEDIO)
                .build();
        }

        return AnaliseLoginResponse.builder()
            .acao(AcaoRecomendada.PERMITIR)
            .reason("AUTENTICACAO_CONFIAVEL")
            .riskScore(RiscoCalculado.BAIXO)
            .build();
    }
}