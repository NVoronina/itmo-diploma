package com.medical.medonline.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.medonline.dto.request.DoctorRequest;
import com.medical.medonline.dto.response.DoctorResponse;
import com.medical.medonline.entity.*;
import com.medical.medonline.exception.NotFoundException;
import com.medical.medonline.exception.ValidationException;
import com.medical.medonline.repository.DoctorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserService userService;
    private final SpecializationService specializationService;
    private final ServiceService serviceService;
    private final ModelMapper modelMapper;

    public DoctorService(DoctorRepository doctorRepository, UserService userService, SpecializationService specializationService, ServiceService serviceService, ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.doctorRepository = doctorRepository;
        this.userService = userService;
        this.specializationService = specializationService;
        this.serviceService = serviceService;
        this.modelMapper = modelMapper;
    }

    public List<DoctorResponse> getDoctors(Long specializationId, Long serviceId) {
        List<DoctorEntity> list = doctorRepository.getAllByServiceAndSpecialisation(specializationId, serviceId);

        return list.stream()
                .map(this::prepareResponse)
                .toList();
    }

    public DoctorResponse getDoctorById(Long doctorId) throws NotFoundException {

        return modelMapper.map(getById(doctorId), DoctorResponse.class);
    }

    public DoctorResponse createDoctor(DoctorRequest request) throws ValidationException {
        DoctorEntity doctorEntity = new DoctorEntity();

        if (request.getSpecializationId() == null) {
            throw new ValidationException("Specialization couldn't be null", 1002);
        }
        if (request.getServiceIds() != null) {
            Set<ServiceEntity> services = serviceService.getServicesByIds(request.getServiceIds());
            doctorEntity.setServices(services);
        }
        UserEntity userEntity = userService.createUser(
                request.getEmail(),
                request.getName(),
                null,
                request.getSecondName(),
                request.getSurname());
        doctorEntity.setUser(userEntity);
        SpecializationEntity specializationEntity = specializationService.getById(request.getSpecializationId());
        if (specializationEntity != null) {
            doctorEntity.setSpecialization(specializationEntity);
        }
        doctorRepository.save(doctorEntity);

        return prepareResponse(doctorEntity);
    }

    private DoctorResponse prepareResponse(DoctorEntity entity) {
        DoctorResponse response = modelMapper.map(entity, DoctorResponse.class);
        response.setName(entity.getUser().getName());
        response.setSurname(entity.getUser().getSurname());
        response.setSecondName(entity.getUser().getSecondName());
        response.setEmail(entity.getUser().getEmail());

        return response;
    }

    public DoctorEntity getById(Long doctorId) throws NotFoundException {
        return doctorRepository
                .findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doctor with id " + doctorId + " not found", 1000));
    }
}
