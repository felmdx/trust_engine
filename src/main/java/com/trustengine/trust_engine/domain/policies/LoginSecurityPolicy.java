package com.trustengine.trust_engine.domain.policies;

import com.trustengine.trust_engine.domain.entities.TentativaLogin;
import com.trustengine.trust_engine.domain.entities.Usuario;
import com.trustengine.trust_engine.domain.enums.RiscoCalculado;

public interface LoginSecurityPolicy {
    
    RiscoCalculado avaliarRisco(TentativaLogin tentativa, Usuario usuario);
}