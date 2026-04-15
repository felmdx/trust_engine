package com.trustengine.trust_engine.domain.repositories;

import com.trustengine.trust_engine.domain.entities.Dispositivo;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface para verificação do Dispositivo
 */
public interface DispositivoRepository {

    Optional<Dispositivo> procuraPorId(UUID id);
    
    Optional<Dispositivo> procuraPorFingerprint(String deviceFingerprint);

    List<Dispositivo> procuraTodosPorUsuarioId(UUID usuarioId);
    
    void salva(Dispositivo Dispositivo);
}