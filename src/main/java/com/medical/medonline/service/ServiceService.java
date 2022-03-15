package com.medical.medonline.service;

import com.medical.medonline.dto.request.ServiceRequest;
import com.medical.medonline.dto.response.PatientResponse;
import com.medical.medonline.dto.response.ServiceResponse;
import com.medical.medonline.entity.DoctorEntity;
import com.medical.medonline.entity.ServiceEntity;
import com.medical.medonline.exception.NotFoundException;
import com.medical.medonline.exception.ValidationException;
import com.medical.medonline.repository.ServiceRepository;
import com.medical.medonline.repository.SpecializationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServiceService {
    private ServiceRepository serviceRepository;
    private ModelMapper modelMapper;

    public final static Integer DEFAULT_TIME_RANGE = 30;

    public ServiceService(ServiceRepository serviceRepository, ModelMapper modelMapper) {
        this.serviceRepository = serviceRepository;
        this.modelMapper = modelMapper;
    }

    public List<ServiceResponse> getServices() {
        List<ServiceEntity> list = serviceRepository.findAll();

        return list.stream()
                .map(service -> modelMapper.map(service, ServiceResponse.class))
                .collect(Collectors.toList());
    }

    public ServiceResponse createService(ServiceRequest serviceRequest) {
        String serviceName = serviceRequest.getService().trim();
        ServiceEntity serviceExist = serviceRepository.findServiceEntityByService(serviceName);
        if (serviceExist != null) {
            throw new ValidationException("Service with name " + serviceName + " already exists", 1000);
        }
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setService(serviceName);
        serviceEntity.setTime(serviceRequest.getTime());
        serviceRepository.save(serviceEntity);

        return modelMapper.map(serviceEntity, ServiceResponse.class);
    }

    public Set<ServiceEntity> getServicesByIds(List<Long> serviceIds) {
        return serviceRepository.findAllByServiceIds(serviceIds);
    }
}
