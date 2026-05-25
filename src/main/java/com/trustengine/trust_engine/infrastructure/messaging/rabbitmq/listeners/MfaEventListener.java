package com.trustengine.trust_engine.infrastructure.messaging.rabbitmq.listeners;

import com.trustengine.trust_engine.application.dtos.events.MfaSolicitadoEvent;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MfaEventListener {

    private final ObjectMapper objectMapper;

    @RabbitListener(queuesToDeclare = @Queue("fila.mfa.solicitado"))
    public void processarEventoDeMfa(String mensagemJson) {
        
        // Simulador de Listener de API de SMS
        try {
            MfaSolicitadoEvent event = objectMapper.readValue(mensagemJson, MfaSolicitadoEvent.class);

            System.out.println("\n==================================================");
            System.out.println("[WORKER] Nova mensagem lida da fila do RabbitMQ!");
            System.out.println("Simulando envio de SMS de Segurança...");
            System.out.println("Para o Usuário ID: " + event.getUserId());
            System.out.println("Motivo do Alerta: " + event.getReason());
            System.out.println("Registrado às: " + event.getTimestamp());
            System.out.println("✅ Status: SMS Enviado com sucesso.");
            System.out.println("==================================================\n");
        } catch (Exception e) {
            System.err.println("O Worker não conseguiu ler o JSON da fila: " + e.getMessage());
        }
    }
}