package com.trustengine.trust_engine.domain.repositories;

import com.trustengine.trust_engine.domain.entities.TentativaLogin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Interface para regras de confiança
 */
public interface TentativaLoginRepository {

    List<TentativaLogin> procuraTodosPorUsuarioIdOrderByTimestampDesc(UUID usuarioId);
    
    void salva(TentativaLogin TentativaLogin);

    long countFalhaPorIpDesde(String ipOrigem, LocalDateTime inicio);
    
    long countFalhaPorUserDesde(UUID usuarioId, LocalDateTime inicio);
}