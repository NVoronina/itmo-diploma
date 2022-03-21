package com.medical.medonline.controller;

import com.medical.medonline.dto.request.DoctorRequest;
import com.medical.medonline.dto.request.DoctorsTimetableRequest;
import com.medical.medonline.dto.request.TimetableRequest;
import com.medical.medonline.dto.response.DoctorResponse;
import com.medical.medonline.dto.response.DoctorsTimetableResponse;
import com.medical.medonline.entity.SpecializationEntity;
import com.medical.medonline.repository.DoctorsTimetableRepository;
import com.medical.medonline.repository.SpecializationRepository;
import com.medical.medonline.service.DoctorService;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DoctorsTimetableControllerTest extends AbstractToken {

    @Autowired
    SpecializationRepository specializationRepository;

    @Autowired
    DoctorsTimetableRepository timetableRepository;

    @Autowired
    DoctorService doctorService;

    @Test
    @Transactional
    void createTimetable() throws Exception {

        List<TimetableRequest> list = new ArrayList<>();
        list.add(new TimetableRequest("2022-03-13T09:00:00", "2022-03-13T13:00:00"));
        list.add(new TimetableRequest("2022-03-14T09:00:00", "2022-03-14T13:00:00"));
        Long doctorId = createDoctor();
        DoctorsTimetableRequest requestTimetable = new DoctorsTimetableRequest(doctorId, list);
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/timetable")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestTimetable));
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();
        DoctorsTimetableResponse responseTimetables = objectMapper.readValue(content, DoctorsTimetableResponse.class);

        assertEquals(doctorId, responseTimetables.getDoctorId());
        assertEquals(list.size(), responseTimetables.getTimetables().size());
    }

    @Test
    @Transactional
    void getTimetables() throws Exception {
        ResultActions perform = this.mockMvc.perform(get("/api/v1/timetable/list?doctorId=" + createDoctor())
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
    void deleteTimetableNotFound() throws Exception {
        this.mockMvc.perform(delete("/api/v1/timetable/" + 100500)
                        .header("Authorization", "Bearer " + TOKEN))
                .andDo(print())
                .andExpect(status().is(404));
    }

    private Long createDoctor() {
        SpecializationEntity specializationEntity = new SpecializationEntity();
        specializationEntity.setSpecialization("Стоматолог");
        specializationRepository.save(specializationEntity);
        DoctorRequest request = new DoctorRequest(
                specializationEntity.getId(),
                "Peter",
                "Doe",
                "Petrovich",
                "pete@test.ru",
                new ArrayList<>()
        );
        DoctorResponse doctorResponse = doctorService.createDoctor(request);

        return doctorResponse.getId();
    }
}