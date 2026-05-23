package com.trustengine.trust_engine.application.usecases;

import com.trustengine.trust_engine.domain.entities.Dispositivo;
import com.trustengine.trust_engine.domain.repositories.DispositivoRepository;
import com.trustengine.trust_engine.presentation.payloads.requests.VincularDispositivoRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VincularDispositivoUseCase {

    private final DispositivoRepository dispositivoRepository;

    public void execute(VincularDispositivoRequest request) {
        
        Optional<Dispositivo> existente = dispositivoRepository.procuraPorFingerprint(request.getDeviceFingerprint());

        if (existente.isPresent()) {
            Dispositivo disp = existente.get();
            if (!disp.isConfiavel()) {
                // Barra se tentar cadastrar um aparelho que já foi revogado no passado por fraude
                throw new IllegalStateException("Este dispositivo encontra-se na blocklist (Revogado).");
            }
            // Se já existe e é confiável, não precisamos fazer nada (idempotencia)
            return; 
        }

        Dispositivo novoDispositivo = new Dispositivo(
                request.getUserId(),
                request.getDeviceFingerprint(),
                request.getModelo()
        );

        dispositivoRepository.salva(novoDispositivo);
    }
}