package com.medical.medonline.service;

import com.medical.medonline.dto.response.PatientResponse;
import com.medical.medonline.entity.DoctorEntity;
import com.medical.medonline.entity.PatientEntity;
import com.medical.medonline.exception.NotFoundException;
import com.medical.medonline.repository.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private PatientRepository patientRepository;
    private DoctorService doctorService;
    private ModelMapper modelMapper;

    public PatientService(PatientRepository patientRepository, DoctorService doctorService, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
    }

    public List<PatientResponse> getPatientByDoctorId(Long doctorId) throws NotFoundException {
        doctorService.getById(doctorId);
        List<PatientEntity> list = patientRepository.getByDoctorId(doctorId);

        return list.stream()
                .map(patient -> {
                    PatientResponse patientResponse = modelMapper.map(patient, PatientResponse.class);
                    if (patient.getUser() != null) {
                        patientResponse.setName(patient.getUser().getName());
                        patientResponse.setSurname(patient.getUser().getSurname());
                        patientResponse.setSecondName(patient.getUser().getSecondName());
                    }
                    return patientResponse;
                })
                .collect(Collectors.toList());
    }
}
