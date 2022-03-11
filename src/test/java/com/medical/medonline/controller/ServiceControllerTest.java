package com.medical.medonline.controller;

import com.medical.medonline.dto.request.ServiceRequest;
import com.medical.medonline.dto.response.ServiceResponse;
import com.medical.medonline.entity.ServiceEntity;
import com.medical.medonline.repository.ServiceRepository;
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
class ServiceControllerTest extends AbstractToken {

    @Autowired
    private ServiceRepository serviceRepository;

    @Test
    public void shouldReturnedSuccessGetList() throws Exception {
        ResultActions perform = this.mockMvc.perform(get("/api/v1/service/list")
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
        ServiceRequest request = new ServiceRequest("new");

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/service")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();
        ServiceResponse responseService = objectMapper.readValue(content, ServiceResponse.class);

        assertEquals(request.getService(), responseService.getService());
    }

    @Test
    public void shouldReturnedException() throws Exception {
        ServiceEntity entity = new ServiceEntity();
        entity.setService("test");
        serviceRepository.save(entity);

        ServiceRequest request = new ServiceRequest("test");

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/service")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        ResultActions perform = this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }
}