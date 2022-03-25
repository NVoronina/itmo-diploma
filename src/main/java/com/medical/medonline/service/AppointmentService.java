package com.medical.medonline.service;

import com.medical.medonline.dto.request.AppointmentRequest;
import com.medical.medonline.dto.request.AppointmentUpdateRequest;
import com.medical.medonline.dto.response.AppointmentResponse;
import com.medical.medonline.dto.response.AppointmentTimesResponse;
import com.medical.medonline.dto.response.DoctorsAppointmentTimesResponse;
import com.medical.medonline.dto.response.ServiceResponse;
import com.medical.medonline.entity.*;
import com.medical.medonline.exception.NotFoundException;
import com.medical.medonline.exception.ValidationException;
import com.medical.medonline.repository.AppointmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        LocalDateTime timeStart = LocalDateTime.parse(request.getTimeStart()).truncatedTo(ChronoUnit.MINUTES);
        Set<ServiceEntity> services = doctorEntity.getServices()
                .stream()
                .filter(serviceEntity -> request.getServiceIds().contains(serviceEntity.getId()))
                .collect(Collectors.toSet());
        if (services.size() < request.getServiceIds().size()) {
            throw new ValidationException("Service not applicable to doctor", 1004);
        }
        if (timeStart.isBefore(LocalDateTime.now()) || timeStart.isAfter(LocalDateTime.now().plusYears(1))) {
            throw new ValidationException("Date could't be before now or grater than year", 1005);
        }
        PatientEntity patientEntity = patientService.getById(request.getPatientId());
        int appointmentDuration = services.stream().mapToInt(el -> {
            if (el.getTime() == null) {
                return ServiceService.DEFAULT_TIME_RANGE;
            }
            return el.getTime();
        }).sum();
        LocalDateTime timeEnd = LocalDateTime.parse(request.getTimeStart()).plusMinutes(appointmentDuration).truncatedTo(ChronoUnit.MINUTES);

        List<AppointmentEntity> appointmentsExist = appointmentRepository.getAppointmentsByTimeRangeAndDoctor(
                doctorEntity,
                timeStart,
                timeEnd);
        if (!appointmentsExist.isEmpty()) {
            throw new ValidationException("Selected datetime is already used", 1011);
        }
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setDoctor(doctorEntity);
        appointmentEntity.setServices(services);
        appointmentEntity.setPatient(patientEntity);
        appointmentEntity.setTimeStart(timeStart);
        appointmentEntity.setTimeEnd(timeEnd);

        appointmentRepository.save(appointmentEntity);

        return modelMapper.map(appointmentEntity, AppointmentResponse.class);
    }

    public void delete(long id) {
        AppointmentEntity appointmentEntity = appointmentRepository.getById(id);
        if (appointmentEntity.getTimeStart() != null) {
            appointmentRepository.delete(appointmentEntity);
        }
    }

    public AppointmentResponse updateAppointment(AppointmentUpdateRequest request) {
        AppointmentEntity appointmentEntity = appointmentRepository.getById(request.getId());
        if (appointmentEntity.getTimeStart() == null) {
            throw new NotFoundException("No appointment found", 1009);
        }
        LocalDateTime timeStart = LocalDateTime.parse(request.getTimeStart()).truncatedTo(ChronoUnit.MINUTES);
        long duration = ChronoUnit.MINUTES.between(appointmentEntity.getTimeStart(), appointmentEntity.getTimeEnd());
        LocalDateTime timeEnd = timeStart.plusMinutes(duration).truncatedTo(ChronoUnit.MINUTES);

        List<AppointmentEntity> appointmentsExist = appointmentRepository.getAppointmentsByTimeRangeAndDoctor(
                appointmentEntity.getDoctor(),
                timeStart,
                timeEnd);
        if (!appointmentsExist.isEmpty()) {
            throw new ValidationException("Selected datetime is already used", 1011);
        }
        if (timeStart.isBefore(LocalDateTime.now()) || timeStart.isAfter(LocalDateTime.now().plusYears(1))) {
            throw new ValidationException("Date could't be before now or grater than year", 1005);
        }
        appointmentEntity.setTimeStart(timeStart);
        appointmentEntity.setTimeEnd(timeEnd);
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
                .map(appointment -> new AppointmentTimesResponse(
                                appointment.getTimeStart().toString(),
                                appointment.getTimeEnd().toString(),
                                appointment.getServices()
                                        .stream()
                                        .map(serviceEntity -> modelMapper.map(serviceEntity, ServiceResponse.class))
                                        .toList()
                        )
                )
                .toList();
        response.setAppointments(list);

        return response;
    }
}
