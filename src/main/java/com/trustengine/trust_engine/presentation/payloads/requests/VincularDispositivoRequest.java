package com.trustengine.trust_engine.presentation.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VincularDispositivoRequest {

    @NotNull(message = "O ID do usuário é obrigatório.")
    private UUID userId;

    @NotBlank(message = "O fingerprint é obrigatório.")
    private String deviceFingerprint;

    @NotBlank(message = "O modelo/User-Agent é obrigatório.")
    private String modelo;
}