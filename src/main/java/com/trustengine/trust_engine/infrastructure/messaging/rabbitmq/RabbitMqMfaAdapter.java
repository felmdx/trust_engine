package com.trustengine.trust_engine.infrastructure.messaging.rabbitmq;


import com.trustengine.trust_engine.application.dtos.events.MfaSolicitadoEvent;
import com.trustengine.trust_engine.application.ports.MfaEventPublisher;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.exc.JsonNodeException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMqMfaAdapter implements MfaEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    
    private static final String FILA_MFA = "fila.mfa.solicitado";

    @Override
    public void publish(MfaSolicitadoEvent event) {
        try {
            String jsonPayload = objectMapper.writeValueAsString(event);
            
            rabbitTemplate.convertAndSend(FILA_MFA, jsonPayload);
            
            System.out.println("📬 [MENSAGERIA] Evento de MFA convertido para JSON e enviado: " + event.getUserId());
            
        } catch (Exception e) {
            System.err.println("Erro crítico ao converter evento para JSON: " + e.getMessage());
        }
    }
}