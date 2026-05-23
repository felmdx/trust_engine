package com.trustengine.trust_engine.application.dtos;

import com.trustengine.trust_engine.domain.enums.AcaoRecomendada;
import com.trustengine.trust_engine.domain.enums.RiscoCalculado;

public record AnaliseLoginResponse(
    AcaoRecomendada acao,
    String justificativa,
    RiscoCalculado risco
) {}