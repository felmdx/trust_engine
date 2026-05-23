package com.trustengine.trust_engine.application.usecases;

import com.trustengine.trust_engine.application.dtos.DispositivoConfiavelResponse;
import com.trustengine.trust_engine.domain.entities.Dispositivo;
import com.trustengine.trust_engine.domain.repositories.DispositivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListarDispositivosUseCase {

    private final DispositivoRepository dispositivoRepository;

    public List<DispositivoConfiavelResponse> execute(UUID userId) {
        List<Dispositivo> dispositivos = dispositivoRepository.procuraTodosPorUsuarioId(userId);

        return dispositivos.stream()
                .filter(Dispositivo::isConfiavel)
                .map(d -> {
                    String fingerprint = d.getDeviceFingerprint();
                    
                    String ultimosQuatro = fingerprint.length() > 4
                            ? fingerprint.substring(fingerprint.length() - 4).toUpperCase()
                            : fingerprint.toUpperCase();

                    return new DispositivoConfiavelResponse(
                            d.getId(),
                            d.getModelo() + " (Final " + ultimosQuatro + ")",
                            d.getDataVinculacao()
                    );
                })
                .collect(Collectors.toList());
    }
}