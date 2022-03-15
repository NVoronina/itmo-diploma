package com.medical.medonline.service;

import com.medical.medonline.dto.request.SpecializationRequest;
import com.medical.medonline.dto.response.SpecializationResponse;
import com.medical.medonline.entity.SpecializationEntity;
import com.medical.medonline.exception.NotFoundException;
import com.medical.medonline.exception.ValidationException;
import com.medical.medonline.repository.SpecializationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpecializationService {
    final private SpecializationRepository specializationRepository;
    final private ModelMapper modelMapper;

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

    public List<SpecializationResponse> getSpecializations() {
        List<SpecializationEntity> list = specializationRepository.findAll();

        return list.stream()
                .map(specialization -> modelMapper.map(specialization, SpecializationResponse.class))
                .collect(Collectors.toList());
    }

    public SpecializationResponse createSpecialization(SpecializationRequest specializationRequest) {
        String name = specializationRequest.getSpecialization().trim();
        SpecializationEntity specializationExist = specializationRepository.findSpecializationEntityBySpecialization(name);
        if (specializationExist != null) {
            throw new ValidationException("Specialization with name " + name + " already exists", 1000);
        }
        SpecializationEntity specializationEntity = new SpecializationEntity();
        specializationEntity.setSpecialization(name);
        specializationRepository.save(specializationEntity);

        return modelMapper.map(specializationEntity, SpecializationResponse.class);
    }
}
