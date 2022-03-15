package com.medical.medonline.service;

import com.medical.medonline.dto.request.PatientRequest;
import com.medical.medonline.dto.request.PatientUpdateRequest;
import com.medical.medonline.dto.response.PatientResponse;
import com.medical.medonline.entity.PatientEntity;
import com.medical.medonline.entity.UserEntity;
import com.medical.medonline.exception.NotFoundException;
import com.medical.medonline.repository.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public PatientService(PatientRepository patientRepository, UserService userService, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public List<PatientResponse> getPatientByDoctorId(Long doctorId) throws NotFoundException {
        List<PatientEntity> list = patientRepository.getByDoctorId(doctorId);

        return list.stream()
                .map(this::prepareResponse)
                .toList();
    }

    public PatientResponse createPatient(PatientRequest request) {
        PatientEntity patientEntity = new PatientEntity();

        if (request.getEmail() != null) {
            UserEntity userEntity = userService.createUser(
                    request.getEmail(),
                    request.getName(),
                    null,
                    request.getSecondName(),
                    request.getSurname());
            patientEntity.setUser(userEntity);
        }
        patientEntity.setContactName(request.getContactName());
        patientEntity.setContactPhone(request.getContactPhone());
        patientEntity.setSnils(request.getSnils());
        patientRepository.save(patientEntity);

        return prepareResponse(patientEntity);
    }

    public PatientResponse updatePatient(PatientUpdateRequest patient) {

        PatientEntity patientEntity = patientRepository.getById(patient.getId());
        patientEntity.setContactPhone(patient.getContactPhone());
        patientEntity.setContactName(patient.getContactName());
        patientRepository.save(patientEntity);

        return prepareResponse(patientEntity);
    }

    private PatientResponse prepareResponse(PatientEntity entity) {
        PatientResponse response = modelMapper.map(entity, PatientResponse.class);
        if (entity.getUser() != null) {
            response.setName(entity.getUser().getName());
            response.setSurname(entity.getUser().getSurname());
            response.setSecondName(entity.getUser().getSecondName());
            response.setEmail(entity.getUser().getEmail());
        }
        return response;
    }

    public PatientEntity getById(Long id) throws NotFoundException {
        Optional<PatientEntity> doctorEntity = patientRepository.findById(id);
        if (doctorEntity.isEmpty()) {
            throw new NotFoundException("Patient with id " + id + " not found", 1000);
        }

        return doctorEntity.get();
    }
}
