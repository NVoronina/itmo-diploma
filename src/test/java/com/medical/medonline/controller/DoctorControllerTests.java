package com.medical.medonline.controller;

import com.medical.medonline.dto.request.DoctorRequest;
import com.medical.medonline.dto.response.DoctorResponse;
import com.medical.medonline.entity.SpecializationEntity;
import com.medical.medonline.repository.SpecializationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
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
public class DoctorControllerTests extends AbstractToken {

    @Autowired
    private SpecializationRepository specializationRepository;

    @Test
    public void shouldReturnedSuccessGetList() throws Exception {
        ResultActions perform = this.mockMvc.perform(get("/api/v1/doctor/list")
                .header("Authorization", "Bearer " + TOKEN))
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
                .header("Authorization", "Bearer " + TOKEN)
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
