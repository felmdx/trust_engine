package com.trustengine.trust_engine.infrastructure.persistence.postgres;

import com.trustengine.trust_engine.domain.entities.Dispositivo;
import org.springframework.stereotype.Component;

@Component
public class DispositivoMapper {

    // Traduz para o banco
    public DispositivoJpaEntity toEntity(Dispositivo dominio) {
        if (dominio == null) return null;

        return DispositivoJpaEntity.builder()
                .id(dominio.getId())
                .usuarioId(dominio.getUsuarioId())
                .deviceFingerprint(dominio.getDeviceFingerprint())
                .modelo(dominio.getModelo())
                .dataVinculacao(dominio.getDataVinculacao())
                .isConfiavel(dominio.isConfiavel())
                .build();
    }

    public Dispositivo toDomain(DispositivoJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;

        return new Dispositivo(
                jpaEntity.getId(),
                jpaEntity.getUsuarioId(),
                jpaEntity.getDeviceFingerprint(),
                jpaEntity.getModelo(),
                jpaEntity.getDataVinculacao(),
                jpaEntity.isConfiavel()
        );
    }
}