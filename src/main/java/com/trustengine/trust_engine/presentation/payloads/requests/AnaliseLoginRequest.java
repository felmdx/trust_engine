package com.trustengine.trust_engine.presentation.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnaliseLoginRequest {

    @NotNull(message = "O ID do usuário é obrigatório.")
    private UUID userId;

    @NotBlank(message = "O endereço IP é obrigatório.")
    @Pattern(regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$|^(?:[A-F0-9]{1,4}:){7}[A-F0-9]{1,4}$", 
             message = "Formato de IP inválido.") // evita buffer Overflow e Injection
    private String ipAddress;

    @NotBlank(message = "O User-Agent é obrigatório.")
    private String userAgent;

    @NotBlank(message = "O fingerprint do dispositivo é obrigatório.")
    private String deviceFingerprint;

    @NotNull(message = "O status da validação da senha é obrigatório.")
    private Boolean isPasswordValid;
    
}