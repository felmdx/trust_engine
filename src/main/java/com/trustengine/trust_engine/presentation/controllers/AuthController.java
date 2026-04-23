package com.trustengine.trust_engine.presentation.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trustengine.trust_engine.presentation.payloads.requests.AnaliseLoginRequest;
import com.trustengine.trust_engine.presentation.payloads.responses.AnaliseLoginResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/analise-login")
    public ResponseEntity<AnaliseLoginResponse> analyzeLogin(@RequestBody @Valid AnaliseLoginRequest request) {
        return ResponseEntity.ok().build();
    }
}