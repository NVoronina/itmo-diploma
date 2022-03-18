package com.medical.medonline.controller;

import com.medical.medonline.dto.request.ManagerRequest;
import com.medical.medonline.dto.response.ManagerResponse;
import com.medical.medonline.service.ManagerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ManagerControllerTest extends AbstractToken {

    @Autowired
    private ManagerService managerService;

    @Test
    void getManagersSuccess() throws Exception {
        ResultActions perform = this.mockMvc.perform(get("/api/v1/manager/list")
                        .header("Authorization", "Bearer " + TOKEN))
                .andDo(print())
                .andExpect(status().is(200));
        MvcResult mvcResult = perform.andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();

        assertFalse(content.isEmpty());
    }

    @Test
    @Transactional
    void createManagerSuccess() throws Exception {
        ManagerRequest request = new ManagerRequest(
                "Bill",
                "Doe",
                null,
                "bill@gggg.ru"
        );

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/manager")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();
        ManagerResponse responseManager = objectMapper.readValue(content, ManagerResponse.class);

        assertEquals(request.getName(), responseManager.getName());
        assertEquals(request.getSecondName(), responseManager.getSecondName());
        assertEquals(request.getSurname(), responseManager.getSurname());
        assertEquals(request.getEmail(), responseManager.getEmail());
    }

    @Test
    @Transactional
    void shouldReturnedSuccessDelete() throws Exception {
        ManagerResponse managerResponse = managerService.createManager(new ManagerRequest(
                "Bill",
                "Doe",
                null,
                "bill@eeee.ru"
        ));
        this.mockMvc.perform(delete("/api/v1/manager/" + managerResponse.getId())
                        .header("Authorization", "Bearer " + TOKEN))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    void shouldReturnedDeleteException() throws Exception {
        this.mockMvc.perform(delete("/api/v1/manager/100500")
                        .header("Authorization", "Bearer " + TOKEN))
                .andDo(print())
                .andExpect(status().is(404));
    }
}