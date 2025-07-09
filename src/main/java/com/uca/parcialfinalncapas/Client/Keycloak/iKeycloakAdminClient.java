package com.uca.parcialfinalncapas.Client.Keycloak;

import com.uca.parcialfinalncapas.Config.Keycloak.KeycloakFeignInterceptorConfig;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "feign-admin", url = "${keycloak.server-url}", configuration = KeycloakFeignInterceptorConfig.class)
public interface iKeycloakAdminClient {
    @PostMapping("/admin/realms/${keycloak.realm}/users")
    Response createUser(@RequestBody Map<String, Object> user);
}
