package com.medical.medonline.service;

import com.medical.medonline.repository.DoctorRepository;
import com.medical.medonline.repository.ManagerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    private ManagerRepository managerRepository;
    private ModelMapper modelMapper;

    public ManagerService(ManagerRepository managerRepository, ModelMapper modelMapper) {
        this.managerRepository = managerRepository;
        this.modelMapper = modelMapper;
    }
}
