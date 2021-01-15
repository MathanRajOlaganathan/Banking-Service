package com.bank.app.controller;

import com.bank.app.data.UserTestDataFactory;
import com.bank.app.domain.dto.UserRequest;
import com.bank.app.domain.dto.UserView;
import org.junit.jupiter.api.Test;

import static com.bank.app.constants.ApplicationConstants.AUTH_LOGIN;
import static com.bank.app.util.JsonHelper.fromJson;
import static com.bank.app.util.JsonHelper.toJson;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Set;

import static java.lang.System.currentTimeMillis;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 14/01/2021
 */
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserTestDataFactory userTestDataFactory;

    private final String password = "password";

    private Set<String> roles =Set.of("ROLE_USER");

    @Autowired
    public AuthControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, UserTestDataFactory userTestDataFactory) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userTestDataFactory = userTestDataFactory;
    }


    @Test
    public void testLoginFail() throws Exception {
        UserView userView = userTestDataFactory.createUser(String.format("Testyio9y2@email.com", currentTimeMillis()), "Test User", password,roles);

        UserRequest request = new UserRequest();
        request.setUsername(userView.getUsername());
        request.setPassword("zxc");

        this.mockMvc
                .perform(post(AUTH_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isUnauthorized())
                .andExpect(header().doesNotExist(HttpHeaders.AUTHORIZATION))
                .andReturn();
}

    }