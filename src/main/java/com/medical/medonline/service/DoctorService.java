package com.medical.medonline.service;

import com.medical.medonline.dto.request.DoctorRequest;
import com.medical.medonline.dto.response.DoctorResponse;
import com.medical.medonline.dto.response.PatientResponse;
import com.medical.medonline.entity.DoctorEntity;
import com.medical.medonline.entity.PatientEntity;
import com.medical.medonline.entity.SpecializationEntity;
import com.medical.medonline.entity.UserEntity;
import com.medical.medonline.exception.NotFoundException;
import com.medical.medonline.exception.ValidationException;
import com.medical.medonline.repository.DoctorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    final private DoctorRepository doctorRepository;
    final private UserService userService;
    final private SpecializationService specializationService;
    final private ModelMapper modelMapper;

    public DoctorService(DoctorRepository doctorRepository, UserService userService, SpecializationService specializationService, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.userService = userService;
        this.specializationService = specializationService;
        this.modelMapper = modelMapper;
    }

    public List<DoctorResponse> getDoctors(Long specializationId, Long serviceId) {
        List<DoctorEntity> list = doctorRepository.getAllByServiceAndSpecialisation(specializationId, serviceId);

        return list.stream()
                .map(doctor -> prepareResponse(doctor))
                .collect(Collectors.toList());
    }

    public DoctorResponse getById(Long doctorId) throws NotFoundException {
        Optional<DoctorEntity> doctorEntity = doctorRepository.findById(doctorId);
        if (doctorEntity.isEmpty()) {
            throw new NotFoundException("Doctor with id " + doctorId + " not found", 1000);
        }
        return modelMapper.map(doctorEntity, DoctorResponse.class);
    }

    public DoctorResponse createDoctor(DoctorRequest request) throws ValidationException {
        if (request.getSpecializationId() == null) {
            throw new ValidationException("Specialization couldn't be null", 1002);
        }
        UserEntity userEntity = userService.createUser(
                request.getEmail(),
                request.getName(),
                null,
                request.getSecondName(),
                request.getSurname());
        DoctorEntity doctorEntity = new DoctorEntity();
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
}
