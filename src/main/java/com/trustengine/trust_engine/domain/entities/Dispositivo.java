package com.trustengine.trust_engine.domain.entities;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Dispositivo {
    private final UUID id;
    private final UUID usuarioId;
    private final String deviceFingerprint;
    private final String modelo;
    private final LocalDateTime dataVinculacao;
    private boolean isConfiavel;

    public Dispositivo(UUID usuarioId, String deviceFingerprint, String modelo) {
        if (usuarioId == null || deviceFingerprint == null || deviceFingerprint.isBlank()) {
            throw new IllegalArgumentException("Dados de identificação do dispositivo são obrigatórios.");
        }
        
        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.deviceFingerprint = deviceFingerprint;
        this.modelo = modelo;
        this.isConfiavel = true;
        this.dataVinculacao = LocalDateTime.now();
    }

    public Dispositivo(UUID id, UUID usuarioId, String deviceFingerprint, String modelo, LocalDateTime dataVinculacao, boolean isConfiavel) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.deviceFingerprint = deviceFingerprint;
        this.modelo = modelo;
        this.dataVinculacao = dataVinculacao;
        this.isConfiavel = isConfiavel;
    }

    public void revogarConfianca() {
        this.isConfiavel = false;
    }
}