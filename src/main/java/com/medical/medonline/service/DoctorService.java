package com.medical.medonline.service;

import com.medical.medonline.dto.request.DoctorRequest;
import com.medical.medonline.dto.response.DoctorResponse;
import com.medical.medonline.entity.DoctorEntity;
import com.medical.medonline.entity.SpecializationEntity;
import com.medical.medonline.entity.UserEntity;
import com.medical.medonline.exception.NotFoundException;
import com.medical.medonline.repository.DoctorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private DoctorRepository doctorRepository;
    private UserService userService;
    private SpecializationService specializationService;
    private ModelMapper modelMapper;

    public DoctorService(DoctorRepository doctorRepository, UserService userService, SpecializationService specializationService, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.userService = userService;
        this.specializationService = specializationService;
        this.modelMapper = modelMapper;
    }

    public List<DoctorResponse> getDoctors(Long specializationId, Long serviceId) {
        List<DoctorEntity> list = doctorRepository.getAllByServiceAndSpecialisation(specializationId, serviceId);

        return list.stream()
                .map(doctor -> prepareDoctorResponse(doctor))
                .collect(Collectors.toList());
    }

    public DoctorResponse getById(Long doctorId) throws NotFoundException {
        Optional<DoctorEntity> doctorEntity = doctorRepository.findById(doctorId);
        if (doctorEntity.isEmpty()) {
            throw new NotFoundException("Doctor with id " + doctorId + " not found", 1000);
        }
        return modelMapper.map(doctorEntity, DoctorResponse.class);
    }

    public DoctorResponse createDoctor(DoctorRequest request) {
        UserEntity userEntity = userService.createUser(
                request.getEmail(),
                request.getName(),
                null,
                request.getSecondName(),
                request.getSurname());
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setUser(userEntity);
        if (request.getSpecializationId() != null) {
            doctorEntity.setSpecialization(specializationService.getById(request.getSpecializationId()));
        }
        doctorRepository.save(doctorEntity);

        return prepareDoctorResponse(doctorEntity);
    }

    private DoctorResponse prepareDoctorResponse(DoctorEntity doctor) {
        DoctorResponse doctorResponse = modelMapper.map(doctor, DoctorResponse.class);
        doctorResponse.setName(doctor.getUser().getName());
        doctorResponse.setSurname(doctor.getUser().getSurname());
        doctorResponse.setSecondName(doctor.getUser().getSecondName());
        doctorResponse.setEmail(doctor.getUser().getEmail());

        return doctorResponse;
    }
}
