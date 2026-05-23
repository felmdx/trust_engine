package com.trustengine.trust_engine.infrastructure.persistence.postgres;

import com.trustengine.trust_engine.domain.entities.Dispositivo;
import com.trustengine.trust_engine.domain.repositories.DispositivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DispositivoRepositoryImpl implements DispositivoRepository {

    private final DispositivoJpaRepository jpaRepository;
    private final DispositivoMapper mapper;

    @Override
    public Optional<Dispositivo> procuraPorId(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Dispositivo> procuraPorFingerprint(String deviceFingerprint) {
        return jpaRepository.findByDeviceFingerprint(deviceFingerprint)
                .map(mapper::toDomain);
    }

    @Override
    public List<Dispositivo> procuraTodosPorUsuarioId(UUID usuarioId) {
        return jpaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(mapper::toDomain) // Transforma toda a lista JPA em lista de Domínio
                .collect(Collectors.toList());
    }

    @Override
    public void salva(Dispositivo dispositivo) {
        DispositivoJpaEntity jpaEntity = mapper.toEntity(dispositivo);
        jpaRepository.save(jpaEntity);
    }
}