package com.medical.medonline.service;

import com.medical.medonline.dto.request.ManagerRequest;
import com.medical.medonline.dto.response.ManagerResponse;
import com.medical.medonline.entity.*;
import com.medical.medonline.exception.ValidationException;
import com.medical.medonline.repository.ManagerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {
    final private ManagerRepository managerRepository;
    final private ModelMapper modelMapper;
    final private UserService userService;

    public ManagerService(ManagerRepository managerRepository, ModelMapper modelMapper, UserService userService) {
        this.managerRepository = managerRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public List<ManagerResponse> getManagers() {
        List<ManagerEntity> list = managerRepository.findAll();

        return list.stream()
                .map(manager -> prepareResponse(manager))
                .collect(Collectors.toList());
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
        ManagerResponse response = modelMapper.map(entity, ManagerResponse.class);
        response.setName(entity.getUser().getName());
        response.setSurname(entity.getUser().getSurname());
        response.setSecondName(entity.getUser().getSecondName());
        response.setEmail(entity.getUser().getEmail());

        return response;
    }

}
