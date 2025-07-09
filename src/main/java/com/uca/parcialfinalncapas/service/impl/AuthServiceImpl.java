package com.uca.parcialfinalncapas.service.impl;

import com.uca.parcialfinalncapas.Client.Keycloak.iKeycloakAdminClient;
import com.uca.parcialfinalncapas.Client.Keycloak.iKeycloakAuthClient;
import com.uca.parcialfinalncapas.Config.Keycloak.KeycloakProperties;
import com.uca.parcialfinalncapas.dto.request.UserCreateRequest;
import com.uca.parcialfinalncapas.dto.response.KeycloakTokenResponse;
import com.uca.parcialfinalncapas.repository.UserRepository;
import com.uca.parcialfinalncapas.service.UserService;
import com.uca.parcialfinalncapas.service.iAuthService;
import com.uca.parcialfinalncapas.utils.mappers.UserMapper;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import static com.uca.parcialfinalncapas.utils.UserIdFromKeycloak.getUserIdFromKeycloakResponse;
import static com.uca.parcialfinalncapas.utils.mappers.UserMapper.loginToFormData;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements iAuthService {

    private final iKeycloakAdminClient keycloakAdminClient;
    private final iKeycloakAuthClient keycloakAuthClient;
    private final KeycloakProperties keycloakProperties;
    @Autowired
    private UserRepository userRepository;

    @Override
    public KeycloakTokenResponse register(UserCreateRequest user) throws Exception {
        Response response = keycloakAdminClient.createUser(UserMapper.createUserDtoToMap(user));
        if (response.status() != 201) throw new Exception("Failed to create user: " + new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8));
        String userId = getUserIdFromKeycloakResponse(response);
        UserService u = new UserServiceImpl(userRepository);
        u.save(user);
        return login(user.getCorreo(), user.getPassword());
    }

    @Override
    public KeycloakTokenResponse login(String email, String password) {
        return keycloakAuthClient.getToken(loginToFormData(email, password, keycloakProperties.getClientId(), keycloakProperties.getClientSecret()));
    }
}
