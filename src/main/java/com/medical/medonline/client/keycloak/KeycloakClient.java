package com.medical.medonline.client.keycloak;


import com.medical.medonline.client.keycloak.dto.request.KeycloakTokenRequest;
import com.medical.medonline.client.keycloak.dto.response.KeycloakTokenResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@FeignClient(name = "KeycloakClient", url = "${keycloak.auth-server-url}")
public interface KeycloakClient {

    @PostMapping(value = "/realms/${keycloak.realm}/protocol/openid-connect/token",
            consumes = APPLICATION_FORM_URLENCODED_VALUE)
    @Headers("Content-Type: application/x-www-form-urlencoded")
    KeycloakTokenResponse getToken(@RequestBody KeycloakTokenRequest request);

}
