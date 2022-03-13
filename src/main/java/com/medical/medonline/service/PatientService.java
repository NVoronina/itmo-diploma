package com.medical.medonline.service;

import com.medical.medonline.dto.request.DoctorRequest;
import com.medical.medonline.dto.request.PatientRequest;
import com.medical.medonline.dto.request.PatientUpdateRequest;
import com.medical.medonline.dto.response.DoctorResponse;
import com.medical.medonline.dto.response.PatientResponse;
import com.medical.medonline.entity.DoctorEntity;
import com.medical.medonline.entity.PatientEntity;
import com.medical.medonline.entity.UserEntity;
import com.medical.medonline.exception.NotFoundException;
import com.medical.medonline.exception.ValidationException;
import com.medical.medonline.repository.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private PatientRepository patientRepository;
    private DoctorService doctorService;
    private UserService userService;
    private ModelMapper modelMapper;

    public PatientService(PatientRepository patientRepository, DoctorService doctorService, UserService userService, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.doctorService = doctorService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public List<PatientResponse> getPatientByDoctorId(Long doctorId) throws NotFoundException {
        doctorService.getDoctorById(doctorId);
        List<PatientEntity> list = patientRepository.getByDoctorId(doctorId);

        return list.stream()
                .map(patient -> prepareResponse(patient))
                .collect(Collectors.toList());
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
        response.setName(entity.getUser().getName());
        response.setSurname(entity.getUser().getSurname());
        response.setSecondName(entity.getUser().getSecondName());
        response.setEmail(entity.getUser().getEmail());

        return response;
    }
}
