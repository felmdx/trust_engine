package com.trustengine.trust_engine.domain.entities;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

import com.trustengine.trust_engine.domain.enums.RiscoCalculado;

@Getter
public class TentativaLogin {
    private final UUID id;
    private final UUID usuarioId;
    private final String ipOrigem;
    private final String userAgent;
    private final LocalDateTime timestamp;
    private final boolean senhaCorreta;
    private final RiscoCalculado riscoCalculado;

    public TentativaLogin(UUID usuarioId, String ipOrigem, String userAgent, boolean senhaCorreta, RiscoCalculado riscoCalculado) {
        valida(usuarioId, ipOrigem);
        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.ipOrigem = ipOrigem;
        this.userAgent = userAgent;
        this.senhaCorreta = senhaCorreta;
        this.riscoCalculado = riscoCalculado;
        this.timestamp = LocalDateTime.now();
    }

    public TentativaLogin(UUID id, UUID usuarioId, String ipOrigem, String userAgent, LocalDateTime timestamp, boolean senhaCorreta, RiscoCalculado riscoCalculado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.ipOrigem = ipOrigem;
        this.userAgent = userAgent;
        this.timestamp = timestamp;
        this.senhaCorreta = senhaCorreta;
        this.riscoCalculado = riscoCalculado;
    }

    public void valida(UUID usuarioId, String ipOrigem){
        if (usuarioId == null || ipOrigem == null || ipOrigem.isBlank()) {
            throw new IllegalArgumentException("Usuário e IP de origem são obrigatórios para registrar a tentativa.");
        }
    }
}