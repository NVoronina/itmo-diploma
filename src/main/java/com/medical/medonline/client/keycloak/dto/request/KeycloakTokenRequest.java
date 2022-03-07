package com.medical.medonline.client.keycloak.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakTokenRequest {
    private String username;
    private String password;
    private String grant_type;
    private String client_id;
    private String client_secret;
}
