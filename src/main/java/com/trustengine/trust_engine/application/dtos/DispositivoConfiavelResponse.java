package com.trustengine.trust_engine.application.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record DispositivoConfiavelResponse(
    UUID deviceId,
    String nomeMascarado, // Ex: iPhone (Final 8A9F)
    LocalDateTime dataUltimoAcesso
) {}