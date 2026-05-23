package com.trustengine.trust_engine.application.usecases;

import com.trustengine.trust_engine.domain.enums.AcaoRecomendada;
import com.trustengine.trust_engine.domain.enums.RiscoCalculado;
import com.trustengine.trust_engine.presentation.payloads.requests.AnaliseLoginRequest;
import com.trustengine.trust_engine.presentation.payloads.responses.AnaliseLoginResponse;

import org.springframework.stereotype.Service;

@Service
public class AnaliseLoginUseCase {

    public AnaliseLoginResponse execute(AnaliseLoginRequest request) {
        
        if (!request.getIsPasswordValid()) {
            return AnaliseLoginResponse.builder()
                .acao(AcaoRecomendada.NEGAR)
                .reason("CREDENCIAIS_INVALIDAS")
                .riskScore(RiscoCalculado.ALTO)
                .build();
        }

        if (request.getDeviceFingerprint() == null || request.getDeviceFingerprint().isBlank()) {
            return AnaliseLoginResponse.builder()
                .acao(AcaoRecomendada.EXIGIR_MFA)
                .reason("DISPOSITIVO_NAO_RECONHECIDO")
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