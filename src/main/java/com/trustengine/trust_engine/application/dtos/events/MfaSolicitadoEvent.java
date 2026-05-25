package com.trustengine.trust_engine.application.dtos.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MfaSolicitadoEvent {
    private String userId;
    private String reason;
    private LocalDateTime timestamp;
}