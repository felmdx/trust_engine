package com.trustengine.trust_engine.presentation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trustengine.trust_engine.application.usecases.AnaliseLoginUseCase;
import com.trustengine.trust_engine.presentation.payloads.requests.AnaliseLoginRequest;
import com.trustengine.trust_engine.presentation.payloads.responses.AnaliseLoginResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AnaliseLoginUseCase analyzeLoginUseCase;

    @PostMapping(value = "/analise-login", consumes = "application/json")
    public ResponseEntity<AnaliseLoginResponse> analyze(@Valid @RequestBody AnaliseLoginRequest request) {
        AnaliseLoginResponse response = analyzeLoginUseCase.execute(request);
        return ResponseEntity.ok(response);
    }
}