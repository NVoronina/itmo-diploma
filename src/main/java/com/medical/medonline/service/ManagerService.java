package com.medical.medonline.service;

import com.medical.medonline.converter.CustomConverter;
import com.medical.medonline.dto.request.ManagerRequest;
import com.medical.medonline.dto.response.ManagerResponse;
import com.medical.medonline.entity.*;
import com.medical.medonline.exception.ValidationException;
import com.medical.medonline.repository.ManagerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CustomConverter converter;

    public ManagerService(ManagerRepository managerRepository, ModelMapper modelMapper, UserService userService, CustomConverter converter) {
        this.managerRepository = managerRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.converter = converter;
    }

    public List<ManagerResponse> getManagers() {
        List<ManagerEntity> list = managerRepository.findAll();

        return list.stream()
                .map(this::prepareResponse)
                .toList();
    }

    public ManagerResponse createManager(ManagerRequest request) throws ValidationException {
        UserEntity userEntity = userService.createUser(
                request.getEmail(),
                request.getName(),
                null,
                request.getSecondName(),
                request.getSurname());
        ManagerEntity managerEntity = new ManagerEntity();
        managerEntity.setUser(userEntity);

        managerRepository.save(managerEntity);

        return prepareResponse(managerEntity);
    }

    public void delete(long id) {
        ManagerEntity managerEntity = managerRepository.getById(id);
        if (managerEntity.getUser() != null) {
            managerRepository.delete(managerEntity);
        }
    }

    private ManagerResponse prepareResponse(ManagerEntity entity) {
        modelMapper.addConverter(converter.getManagerConverter());

        return modelMapper.map(entity, ManagerResponse.class);
    }

}
