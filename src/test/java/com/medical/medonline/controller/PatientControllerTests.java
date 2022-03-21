package com.medical.medonline.controller;

import com.medical.medonline.dto.request.DoctorRequest;
import com.medical.medonline.dto.request.PatientRequest;
import com.medical.medonline.dto.request.PatientUpdateRequest;
import com.medical.medonline.dto.response.DoctorResponse;
import com.medical.medonline.dto.response.ErrorResponse;
import com.medical.medonline.dto.response.PatientResponse;
import com.medical.medonline.entity.SpecializationEntity;
import com.medical.medonline.repository.SpecializationRepository;
import com.medical.medonline.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTests extends AbstractToken {

    @Autowired
    DoctorService doctorService;

    @Autowired
    SpecializationRepository specializationRepository;

    private static long patientCreatedId;

    @Test
    @Transactional
    void shouldReturnedSuccessGetList() throws Exception {
        SpecializationEntity specializationEntity = new SpecializationEntity();
        specializationEntity.setSpecialization("Стоматолог");
        specializationRepository.save(specializationEntity);
        DoctorRequest request = new DoctorRequest(
                specializationEntity.getId(),
                "Peter",
                "Doe",
                "Petrovich",
                "pete@eee.ru",
                new ArrayList<>()
        );
        DoctorResponse doctorResponse = doctorService.createDoctor(request);

        ResultActions perform = this.mockMvc.perform(get("/api/v1/patient/list?doctorId=" + doctorResponse.getId())
                .header("Authorization", "Bearer " + TOKEN))
                .andDo(print())
                .andExpect(status().is(200));
        MvcResult mvcResult = perform.andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();

        assertFalse(content.isEmpty());
    }

    @Test
    void shouldReturnedSuccessPost() throws Exception {
        PatientRequest request = new PatientRequest(
                "Peter",
                "Doe",
                "Petrovich",
                "pete@iii.ru",
                "Peter",
                "+7921999999999",
                "123123123"
        );

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/patient")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();
        PatientResponse responsePatient = objectMapper.readValue(content, PatientResponse.class);

        patientCreatedId = responsePatient.getId();
        assertEquals(request.getName(), responsePatient.getName());
        assertEquals(request.getSecondName(), responsePatient.getSecondName());
        assertEquals(request.getSurname(), responsePatient.getSurname());
        assertEquals(request.getEmail(), responsePatient.getEmail());
        assertEquals(request.getContactName(), responsePatient.getContactName());
        assertEquals(request.getContactPhone(), responsePatient.getContactPhone());
        assertEquals(request.getSnils(), responsePatient.getSnils());
    }

    @Test
    @DependsOn({"shouldReturnedSuccessPost"})
    void shouldReturnedSuccessPut() throws Exception {

        PatientUpdateRequest request = new PatientUpdateRequest(patientCreatedId, "Natali", "+78888888888");

        MockHttpServletRequestBuilder requestBuilder = put("/api/v1/patient")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        ResultActions perform = this.mockMvc.perform(requestBuilder);
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();
        PatientResponse responsePatient = objectMapper.readValue(content, PatientResponse.class);

        assertEquals(request.getContactName(), responsePatient.getContactName());
        assertEquals(request.getContactPhone(), responsePatient.getContactPhone());
    }

    @Test
    void shouldReturnedFailPut() throws Exception {

        PatientUpdateRequest request = new PatientUpdateRequest(100500, "Natali", "+78888888888");

        MockHttpServletRequestBuilder requestBuilder = put("/api/v1/patient")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        ResultActions perform = this.mockMvc.perform(requestBuilder).andExpect(status().is(404));
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();
        ErrorResponse responsePatient = objectMapper.readValue(content, ErrorResponse.class);

        assertEquals(4040, responsePatient.getCode());
    }
}
