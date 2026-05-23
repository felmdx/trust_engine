package com.trustengine.trust_engine.application.usecases;

import com.trustengine.trust_engine.application.dtos.DispositivoConfiavelResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ListarDispositivosUseCase {

    public List<DispositivoConfiavelResponse> execute(UUID userId) {

        // Teste mockado. Todo: Implementar Repository 
        String fingerprintReal = "a1b2c3d4e5f6g7h8i9j0";
        String ultimosQuatro = fingerprintReal.substring(fingerprintReal.length() - 4).toUpperCase();
        
        return List.of(
            new DispositivoConfiavelResponse(
                UUID.randomUUID(), 
                "Smartphone (Final " + ultimosQuatro + ")", 
                LocalDateTime.now().minusDays(2)
            ),
            new DispositivoConfiavelResponse(
                UUID.randomUUID(), 
                "Windows PC (Final A4B2)", 
                LocalDateTime.now()
            )
        );
    }
}