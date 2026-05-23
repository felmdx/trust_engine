package com.trustengine.trust_engine.application.usecases;

import com.trustengine.trust_engine.domain.entities.Dispositivo;
import com.trustengine.trust_engine.domain.repositories.DispositivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RevogarDispositivoUseCase {

    private final DispositivoRepository dispositivoRepository;

    public void execute(UUID userId, UUID deviceId) {
        Dispositivo dispositivo = dispositivoRepository.procuraPorId(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("Dispositivo não encontrado."));

        if (!dispositivo.getUsuarioId().equals(userId)) {
            throw new IllegalArgumentException("Acesso negado: O dispositivo não pertence a este utilizador.");
        }

        dispositivo.revogarConfianca();

        dispositivoRepository.salva(dispositivo);
    }
}