package com.trustengine.trust_engine.presentation.controllers;

import com.trustengine.trust_engine.application.dtos.DispositivoConfiavelResponse;
import com.trustengine.trust_engine.application.usecases.ListarDispositivosUseCase;
import com.trustengine.trust_engine.application.usecases.RevogarDispositivoUseCase;
import com.trustengine.trust_engine.application.usecases.VincularDispositivoUseCase;
import com.trustengine.trust_engine.presentation.payloads.requests.VincularDispositivoRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final VincularDispositivoUseCase vincularDispositivoUseCase;

    // Vincular dispositivo
    @PostMapping
    public ResponseEntity<Void> vincularDispositivo(@Valid @RequestBody VincularDispositivoRequest request) {
        vincularDispositivoUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // Retorna 201 - Criado
    }

    // Listar dispositivos do user
    @GetMapping("/{userId}")
    public ResponseEntity<List<DispositivoConfiavelResponse>> listarDispositivos(@PathVariable UUID userId) {
        List<DispositivoConfiavelResponse> dispositivos = listarDispositivosUseCase.execute(userId);
        return ResponseEntity.ok(dispositivos);
    }

    // Revogar dispositivo
    @PostMapping("/{userId}/revoke/{deviceId}")
    public ResponseEntity<Void> revogarDispositivo(@PathVariable UUID userId, @PathVariable UUID deviceId) {
        revogarDispositivoUseCase.execute(userId, deviceId);
        return ResponseEntity.noContent().build();
    }
}