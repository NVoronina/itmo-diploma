package com.medical.medonline.service;

import com.medical.medonline.dto.response.DoctorResponse;
import com.medical.medonline.entity.DoctorEntity;
import com.medical.medonline.entity.SpecializationEntity;
import com.medical.medonline.exception.NotFoundException;
import com.medical.medonline.repository.SpecializationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
public class SpecializationService {
    private SpecializationRepository specializationRepository;
    private ModelMapper modelMapper;

    public SpecializationService(SpecializationRepository specializationRepository, ModelMapper modelMapper) {
        this.specializationRepository = specializationRepository;
        this.modelMapper = modelMapper;
    }

    public SpecializationEntity getById(Long id) {
        Optional<SpecializationEntity> specializationEntity = specializationRepository.findById(id);
        if (specializationEntity.isEmpty()) {
            throw new NotFoundException("Specialization with id " + id + " not found", 1000);
        }
        return specializationEntity.get();
    }
}
