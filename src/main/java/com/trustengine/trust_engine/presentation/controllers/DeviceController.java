package com.trustengine.trust_engine.presentation.controllers;

import com.trustengine.trust_engine.application.dtos.DispositivoConfiavelResponse;
import com.trustengine.trust_engine.application.usecases.ListarDispositivosUseCase;
import com.trustengine.trust_engine.application.usecases.RevogarDispositivoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final ListarDispositivosUseCase listarDispositivosUseCase;
    private final RevogarDispositivoUseCase revogarDispositivoUseCase;

    // Endpoint Listar
    @GetMapping("/{userId}")
    public ResponseEntity<List<DispositivoConfiavelResponse>> listarDispositivos(@PathVariable UUID userId) {
        List<DispositivoConfiavelResponse> dispositivos = listarDispositivosUseCase.execute(userId);
        return ResponseEntity.ok(dispositivos);
    }

    // Endpoint Revogar
    @PostMapping("/{userId}/revoke/{deviceId}")
    public ResponseEntity<Void> revogarDispositivo(
            @PathVariable UUID userId, 
            @PathVariable UUID deviceId) {
        
        revogarDispositivoUseCase.execute(userId, deviceId);
        return ResponseEntity.noContent().build(); 
    }
}