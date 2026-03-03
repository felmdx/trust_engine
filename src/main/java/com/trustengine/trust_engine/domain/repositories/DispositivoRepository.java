package com.trustengine.trust_engine.domain.repositories;

import com.trustengine.trust_engine.domain.entities.Dispositivo;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface para verificação do Dispositivo
 */
public interface DispositivoRepository {

    Optional<Dispositivo> findById(UUID id);
    
    Optional<Dispositivo> findByFingerprint(String deviceFingerprint);

    List<Dispositivo> findAllByUsuarioId(UUID usuarioId);
    
    void save(Dispositivo Dispositivo);
}