package com.medical.medonline.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.medonline.dto.request.AuthRequest;
import com.medical.medonline.dto.request.DoctorRequest;
import com.medical.medonline.dto.response.DoctorResponse;
import com.medical.medonline.dto.response.TokenResponse;
import com.medical.medonline.entity.SpecializationEntity;
import com.medical.medonline.repository.SpecializationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class DoctorControllerTests {

    private static String token;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SpecializationRepository specializationRepository;

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

    @Test
    public void shouldReturnedSuccessGetList() throws Exception {
        ResultActions perform = this.mockMvc.perform(get("/api/v1/doctor/all")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().is(200));
        MvcResult mvcResult = perform.andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();

        assertFalse(content.isEmpty());
    }

    @Test
    public void shouldReturnedSuccessPost() throws Exception {
        DoctorRequest request = new DoctorRequest(
                (long) 1,
                "Peter",
                "Doe",
                "Petrovich",
                "pete@gggg.ru",
                new ArrayList<>()
        );
        SpecializationEntity specializationEntity = new SpecializationEntity();
        specializationEntity.setSpecialization("Стоматолог");
        specializationRepository.save(specializationEntity);

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/doctor")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();
        DoctorResponse responseDoctor = objectMapper.readValue(content, DoctorResponse.class);

        assertEquals(request.getName(), responseDoctor.getName());
        assertEquals(request.getSecondName(), responseDoctor.getSecondName());
        assertEquals(request.getSurname(), responseDoctor.getSurname());
        assertEquals(request.getEmail(), responseDoctor.getEmail());
        assertEquals(request.getSpecializationId(), responseDoctor.getSpecialization().getId());
    }
}
