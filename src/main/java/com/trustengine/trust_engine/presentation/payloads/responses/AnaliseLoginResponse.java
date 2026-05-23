package com.trustengine.trust_engine.presentation.payloads.responses;

import com.trustengine.trust_engine.domain.enums.AcaoRecomendada;
import com.trustengine.trust_engine.domain.enums.RiscoCalculado;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnaliseLoginResponse {
    private AcaoRecomendada acao; 
    private String reason;
    private RiscoCalculado riskScore;
}