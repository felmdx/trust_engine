package com.trustengine.trust_engine.application.ports;

import com.trustengine.trust_engine.application.dtos.events.MfaSolicitadoEvent;

public interface MfaEventPublisher {
    void publish(MfaSolicitadoEvent event);
}