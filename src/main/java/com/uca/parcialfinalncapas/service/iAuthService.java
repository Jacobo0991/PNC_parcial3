package com.uca.parcialfinalncapas.service;

import com.uca.parcialfinalncapas.dto.request.LogInRequest;
import com.uca.parcialfinalncapas.dto.request.UserCreateRequest;
import com.uca.parcialfinalncapas.dto.response.KeycloakTokenResponse;

public interface iAuthService {

    KeycloakTokenResponse register(UserCreateRequest user) throws Exception;
    KeycloakTokenResponse login(String username, String password);
}
