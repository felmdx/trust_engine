package com.trustengine.trust_engine.domain.repositories;

import com.trustengine.trust_engine.domain.entities.Usuario;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface de contrato para persistência do id.
 */
public interface UsuarioRepository {

    Optional<Usuario> findById(UUID id);
    
    Optional<Usuario> findByEmail(String email);
    
    void save(Usuario usuario);
}