package com.medical.medonline.controller;

import com.medical.medonline.dto.request.AuthRequest;
import com.medical.medonline.dto.response.TokenResponse;
import com.medical.medonline.service.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private KeycloakService keycloakService;

    public AuthController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> getToken(@RequestBody AuthRequest request) {
        return ResponseEntity.ok().body(keycloakService.authUser(request));
    }
}
