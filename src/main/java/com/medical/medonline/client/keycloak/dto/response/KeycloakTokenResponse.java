package com.medical.medonline.client.keycloak.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakTokenResponse {
    private String access_token;
}
