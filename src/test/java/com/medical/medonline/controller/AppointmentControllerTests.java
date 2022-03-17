package com.medical.medonline.controller;

import com.medical.medonline.dto.request.AppointmentRequest;
import com.medical.medonline.dto.request.DoctorRequest;
import com.medical.medonline.dto.request.PatientRequest;
import com.medical.medonline.dto.request.ServiceRequest;
import com.medical.medonline.dto.response.AppointmentResponse;
import com.medical.medonline.dto.response.DoctorResponse;
import com.medical.medonline.dto.response.PatientResponse;
import com.medical.medonline.dto.response.ServiceResponse;
import com.medical.medonline.entity.ServiceEntity;
import com.medical.medonline.entity.SpecializationEntity;
import com.medical.medonline.repository.SpecializationRepository;
import com.medical.medonline.service.DoctorService;
import com.medical.medonline.service.PatientService;
import com.medical.medonline.service.ServiceService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerTests extends AbstractToken {

    // TODO: 17.03.2022 add test with double attmpt to appoint. same doctor, same time, same user/ dif user
    // TODO: 17.03.2022 add test with appontment date next year with 400
    // TODO: 17.03.2022 add test with date appointment before now with 400
    // TODO: 17.03.2022 add test with appointment transfer and success use this slot by other user afterwards.


    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Test
    @Transactional
    public void shouldReturnedSuccessGetList() throws Exception {
        Long doctorId = createDoctor(null);

        ResultActions perform = this.mockMvc.perform(get("/api/v1/appointment/list?doctorId=" + doctorId)
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
    public void shouldReturnedSuccessPost() throws Exception {
        Long serviceId = createService();
        Long doctorId = createDoctor(serviceId);
        Long patientId = createPatient();
        List<Long> listServices = new ArrayList<>();
        listServices.add(serviceId);

        AppointmentRequest request = new AppointmentRequest("2022-03-14T10:00", doctorId, patientId, listServices);
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/appointment")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();
        AppointmentResponse responseAppoint = objectMapper.readValue(content, AppointmentResponse.class);

        assertEquals(request.getDoctorId(), responseAppoint.getDoctor().getId());
        assertEquals(request.getServiceIds().size(), responseAppoint.getServices().size());
        assertEquals(request.getPatientId(), responseAppoint.getPatient().getId());
        assertEquals(request.getTimeStart(), responseAppoint.getTimeStart());
    }

    private Long createService() {
        ServiceRequest serviceRequest = new ServiceRequest("Test1", 15);
        ServiceResponse serviceResponse = serviceService.createService(serviceRequest);

        return serviceResponse.getId();
    }

    private Long createDoctor(Long serviceId) {
        SpecializationEntity specializationEntity = new SpecializationEntity();
        specializationEntity.setSpecialization("Стоматолог");
        specializationRepository.save(specializationEntity);
        List<Long> listService = new ArrayList<>();
        if (serviceId != null) {
            listService.add(serviceId);
        }
        DoctorRequest request = new DoctorRequest(
                specializationEntity.getId(),
                "Peter",
                "Doe",
                "Petrovich",
                "pete@test1.ru",
                listService
        );
        DoctorResponse doctorResponse = doctorService.createDoctor(request);

        return doctorResponse.getId();
    }

    private Long createPatient() {
        PatientRequest request = new PatientRequest(
                null,
                null,
                null,
                null,
                "Test",
                "99999999999",
                "333333"
        );
        PatientResponse response = patientService.createPatient(request);

        return response.getId();
    }
}
