package com.medical.medonline.service;

import com.medical.medonline.dto.request.AppointmentRequest;
import com.medical.medonline.dto.response.AppointmentResponse;
import com.medical.medonline.dto.response.AppointmentTimesResponse;
import com.medical.medonline.dto.response.DoctorsAppointmentTimesResponse;
import com.medical.medonline.dto.response.ServiceResponse;
import com.medical.medonline.entity.*;
import com.medical.medonline.exception.ValidationException;
import com.medical.medonline.repository.AppointmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    public AppointmentService(DoctorService doctorService, PatientService patientService, AppointmentRepository appointmentRepository, ModelMapper modelMapper) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.appointmentRepository = appointmentRepository;
        this.modelMapper = modelMapper;
    }

    public AppointmentResponse createAppointment(AppointmentRequest request) {
        DoctorEntity doctorEntity = doctorService.getById(request.getDoctorId());
        Set<ServiceEntity> services = doctorEntity.getServices()
                .stream()
                .filter(serviceEntity -> request.getServiceIds().contains(serviceEntity.getId()))
                .collect(Collectors.toSet());
        if (services.size() < request.getServiceIds().size()) {
            throw new ValidationException("Service not applicable to doctor", 1004);
        }
        PatientEntity patientEntity = patientService.getById(request.getPatientId());
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setDoctor(doctorEntity);
        appointmentEntity.setServices(services);
        appointmentEntity.setPatient(patientEntity);
        appointmentEntity.setTimeStart(LocalDateTime.parse(request.getTimeStart()));
        appointmentRepository.save(appointmentEntity);

        return modelMapper.map(appointmentEntity, AppointmentResponse.class);
    }

    public DoctorsAppointmentTimesResponse getList(Long doctorId) {
        DoctorEntity doctorEntity = doctorService.getById(doctorId);
        LocalDateTime tillDate = LocalDateTime.now().plusWeeks(1);

        List<AppointmentEntity> appointments = appointmentRepository
                .getAppointmentEntitiesByDoctorAndTimeStartBeforeOrderByTimeStart(doctorEntity, tillDate);
        DoctorsAppointmentTimesResponse response = new DoctorsAppointmentTimesResponse();
        response.setDoctorId(doctorId);
        List<AppointmentTimesResponse> list = appointments.stream()
            .map(appointment -> {
                int timeEnd = appointment.getServices().stream().mapToInt(el -> {
                    if (el.getTime() == null) {
                        return ServiceService.DEFAULT_TIME_RANGE;
                    }
                    return el.getTime();
                }).sum();
                return new AppointmentTimesResponse(
                    appointment.getTimeStart().toString(),
                    appointment.getTimeStart().plusMinutes(timeEnd).toString(),
                    appointment.getServices()
                        .stream()
                        .map(serviceEntity -> modelMapper.map(serviceEntity, ServiceResponse.class))
                        .toList()
                );
            })
            .toList();
        response.setAppointments(list);

        return response;
    }
}
