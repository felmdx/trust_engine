package com.trustengine.trust_engine.application.usecases;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class RevogarDispositivoUseCase {

    public void execute(UUID userId, UUID deviceId) {

        // TOdo ao implementar banco: 
        // 1. Buscar o dispositivo pelo deviceId
        // 2. Verificar se ele realmente pertence ao userId (Prevenção contra IDOR)
        // 3. Mudar o status para REVOGADO no banco
        
        System.out.println("Alerta: Dispositivo " + deviceId + " revogado para o usuário " + userId);
    }
}