package com.medical.medonline.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.medonline.dto.request.AuthRequest;
import com.medical.medonline.dto.response.TokenResponse;
import com.medical.medonline.repository.SpecializationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
abstract public class AbstractToken {
    protected static String token;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    public void setUpClass() throws Exception {
        if (token != null) {
            return;
        }
        AuthRequest request = new AuthRequest("test", "test");

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();
        TokenResponse tokenResponse = objectMapper.readValue(content, TokenResponse.class);
        token = tokenResponse.getToken();
    }

}
