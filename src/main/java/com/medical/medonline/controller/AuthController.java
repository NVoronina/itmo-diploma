package com.medical.medonline.controller;

import com.medical.medonline.dto.request.AuthRequest;
import com.medical.medonline.dto.response.TokenResponse;
import com.medical.medonline.service.KeycloakService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@Tag(name = "Authenticate")
public class AuthController {

    private final KeycloakService keycloakService;

    public AuthController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> getToken(@RequestBody AuthRequest request) {
        return ResponseEntity.ok().body(keycloakService.authUser(request));
    }
}
