package com.medical.medonline.service;

import com.medical.medonline.client.keycloak.KeycloakClient;
import com.medical.medonline.client.keycloak.dto.request.KeycloakTokenRequest;
import com.medical.medonline.client.keycloak.dto.response.KeycloakTokenResponse;
import com.medical.medonline.dto.request.AuthRequest;
import com.medical.medonline.dto.response.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KeycloakService {

    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;
    private final KeycloakClient keycloakClient;

    public KeycloakService(KeycloakClient keycloakClient) {
        this.keycloakClient = keycloakClient;
    }

    public TokenResponse authUser(AuthRequest authRequest) {
        KeycloakTokenResponse response = keycloakClient.getToken(
                new KeycloakTokenRequest(
                        authRequest.getLogin(),
                        authRequest.getPassword(),
                        "password",
                        clientId,
                        clientSecret
                ));

        return new TokenResponse(response.getAccess_token());
    }
}
