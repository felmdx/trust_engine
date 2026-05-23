package com.trustengine.trust_engine.infrastructure.persistence.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DispositivoJpaRepository extends JpaRepository<DispositivoJpaEntity, UUID> {
    
    Optional<DispositivoJpaEntity> findByDeviceFingerprint(String deviceFingerprint);
    List<DispositivoJpaEntity> findByUsuarioId(UUID usuarioId);
}