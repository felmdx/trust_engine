package com.trustengine.trust_engine.application.usecases;

import com.trustengine.trust_engine.application.dtos.events.MfaSolicitadoEvent;
import com.trustengine.trust_engine.application.ports.MfaEventPublisher;
import com.trustengine.trust_engine.domain.entities.Dispositivo;
import com.trustengine.trust_engine.domain.enums.AcaoRecomendada;
import com.trustengine.trust_engine.domain.enums.RiscoCalculado;
import com.trustengine.trust_engine.domain.repositories.DispositivoRepository;
import com.trustengine.trust_engine.presentation.payloads.requests.AnaliseLoginRequest;
import com.trustengine.trust_engine.presentation.payloads.responses.AnaliseLoginResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnaliseLoginUseCase {

    private final DispositivoRepository dispositivoRepository;
    private final MfaEventPublisher mfaEventPublisher;

    public AnaliseLoginResponse execute(AnaliseLoginRequest request) {
        
        if (!request.getIsPasswordValid()) {

            System.out.println("[LOGGER] Senha inválida.");

            return AnaliseLoginResponse.builder()
                .acao(AcaoRecomendada.NEGAR)
                .reason("CREDENCIAIS_INVALIDAS")
                .riskScore(RiscoCalculado.ALTO)
                .build();
        }

        Optional<Dispositivo> dispositivoOpt = dispositivoRepository.procuraPorFingerprint(request.getDeviceFingerprint());

        if (dispositivoOpt.isEmpty() || 
            !dispositivoOpt.get().isConfiavel() || 
            !dispositivoOpt.get().getUsuarioId().toString().equals(request.getUserId().toString())) {

            System.out.println("[LOGGER] Dispositivo não reconhecido: MFA solicitado.");
            
            mfaEventPublisher.publish(MfaSolicitadoEvent.builder()
            .userId(request.getUserId().toString())
            .reason("DISPOSITIVO_NAO_RECONHECIDO")
            .timestamp(LocalDateTime.now())
            .build());
            
            return AnaliseLoginResponse.builder()
                .acao(AcaoRecomendada.EXIGIR_MFA)
                .reason("DISPOSITIVO_NAO_RECONHECIDO_OU_PERTENCE_A_OUTRO_USUARIO")
                .riskScore(RiscoCalculado.MEDIO)
                .build();
        }

        System.out.println("[LOGGER] Senha válida.");

        return AnaliseLoginResponse.builder()
            .acao(AcaoRecomendada.PERMITIR)
            .reason("AUTENTICACAO_CONFIAVEL")
            .riskScore(RiscoCalculado.BAIXO)
            .build();
    }
}