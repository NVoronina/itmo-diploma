package com.medical.medonline.controller;

import com.medical.medonline.dto.request.*;
import com.medical.medonline.dto.response.AppointmentResponse;
import com.medical.medonline.dto.response.DoctorResponse;
import com.medical.medonline.dto.response.PatientResponse;
import com.medical.medonline.dto.response.ServiceResponse;
import com.medical.medonline.entity.SpecializationEntity;
import com.medical.medonline.repository.SpecializationRepository;
import com.medical.medonline.service.AppointmentService;
import com.medical.medonline.service.DoctorService;
import com.medical.medonline.service.PatientService;
import com.medical.medonline.service.ServiceService;
import org.junit.jupiter.api.BeforeEach;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerTests extends AbstractToken {

    // TODO: 17.03.2022 add test with double attmpt to appoint. same doctor, same time, same user/ dif user
    // DONE
    // TODO: 17.03.2022 add test with appontment date next year with 400.
    // DONE
    // TODO: 17.03.2022 add test with date appointment before now with 400
    // DONE
    // TODO: 17.03.2022 add test with appointment transfer and success use this slot by other user afterwards.
    // DONE

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    private Long doctorId;
    private Long patientId;
    private List<Long> listServices;

    @BeforeEach
    public void setUpClass() throws Exception {
        Long serviceId = createService();
        doctorId = createDoctor(serviceId);
        patientId = createPatient();
        listServices = new ArrayList<>();
        listServices.add(serviceId);
    }

    @Test
    @Transactional
    void shouldReturnedSuccessGetList() throws Exception {
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
    void shouldReturnedSuccessPost() throws Exception {

        AppointmentRequest request = new AppointmentRequest(
                LocalDateTime.now().plusWeeks(1).toString(), doctorId, patientId, listServices);
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

    @Test
    @Transactional
    void shouldReturnedErrorPostNextYear() throws Exception {
        AppointmentRequest request = new AppointmentRequest(
                LocalDateTime.now().plusYears(2).toString(),
                doctorId, patientId, listServices);
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/appointment")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    @Transactional
    void shouldReturnedErrorPostBeforeNow() throws Exception {

        AppointmentRequest request = new AppointmentRequest(
                LocalDateTime.now().minusDays(2).toString(),
                doctorId, patientId, listServices);
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/appointment")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    @Transactional
    void shouldReturnedErrorDoublePostSamePatient() throws Exception {

        String time = LocalDateTime.now().plusDays(2).toString();
        AppointmentRequest request = new AppointmentRequest(
                time,
                doctorId, patientId, listServices);
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/appointment")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200));

        requestBuilder = post("/api/v1/appointment")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    @Transactional
    void shouldReturnedErrorDoublePostDifferentPatient() throws Exception {

        String time = LocalDateTime.now().plusDays(2).toString();
        AppointmentRequest request = new AppointmentRequest(
                time,
                doctorId, patientId, listServices);
        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/appointment")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200));

        request.setPatientId(createPatient());
        requestBuilder = post("/api/v1/appointment")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    @Transactional
    void shouldSuccessChangeAppointment() throws Exception {

        AppointmentRequest createRequest = new AppointmentRequest(
                LocalDateTime.now().plusWeeks(1).toString(), doctorId, patientId, listServices);
        Long appointmentId = appointmentService.createAppointment(createRequest).getId();
        AppointmentUpdateRequest request = new AppointmentUpdateRequest(
                appointmentId,
                LocalDateTime.now().plusWeeks(1).toString()
        );
        MockHttpServletRequestBuilder requestBuilder = put("/api/v1/appointment")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();
        AppointmentResponse responseAppoint = objectMapper.readValue(content, AppointmentResponse.class);

        assertEquals(request.getTimeStart(), responseAppoint.getTimeStart());
        assertEquals(request.getId(), responseAppoint.getId());

        Long otherPatientId = createPatient();
        AppointmentRequest createOtherUserRequest = new AppointmentRequest(
                LocalDateTime.now().plusWeeks(1).toString(), doctorId, otherPatientId, listServices);

        requestBuilder = post("/api/v1/appointment")
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOtherUserRequest));
        perform = this.mockMvc.perform(requestBuilder);
        mvcResult = perform.andReturn();
        response = mvcResult.getResponse();
        content = response.getContentAsString();
        AppointmentResponse responseAppointOther = objectMapper.readValue(content, AppointmentResponse.class);

        assertEquals(createOtherUserRequest.getDoctorId(), responseAppointOther.getDoctor().getId());
        assertEquals(createOtherUserRequest.getServiceIds().size(), responseAppointOther.getServices().size());
        assertEquals(createOtherUserRequest.getPatientId(), responseAppointOther.getPatient().getId());
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
