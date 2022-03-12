package com.medical.medonline.controller;

import com.medical.medonline.dto.request.SpecializationRequest;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpecializationControllerTest extends AbstractToken {

    @Autowired
    private SpecializationRepository specializationRepository;

    @Test
    public void shouldReturnedSuccessGetList() throws Exception {
        ResultActions perform = this.mockMvc.perform(get("/api/v1/specialization/list")
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
        SpecializationRequest request = new SpecializationRequest("new");

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/specialization")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();
        SpecializationRequest responseSpecialization = objectMapper.readValue(content, SpecializationRequest.class);

        assertEquals(request.getSpecialization(), responseSpecialization.getSpecialization());
    }

    @Test
    public void shouldReturnedException() throws Exception {
        SpecializationEntity entity = new SpecializationEntity();
        entity.setSpecialization("test");
        specializationRepository.save(entity);

        SpecializationRequest request = new SpecializationRequest("test");

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/specialization")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        ResultActions perform = this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }
}