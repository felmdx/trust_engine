package com.trustengine.trust_engine.domain.repositories;

import com.trustengine.trust_engine.domain.entities.TentativaLogin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Interface para regras de confiança
 */
public interface TentativaLoginRepository {

    List<TentativaLogin> findAllByUsuarioIdOrderByTimestampDesc(UUID usuarioId);
    
    void save(TentativaLogin TentativaLogin);

    long countFailsByIpSince(String ipOrigem, LocalDateTime inicio);
    
    long countFailByUserSince(UUID usuarioId, LocalDateTime inicio);
}