package com.medical.medonline.controller;

import com.medical.medonline.dto.request.DoctorRequest;
import com.medical.medonline.dto.request.ServiceRequest;
import com.medical.medonline.dto.response.DoctorResponse;
import com.medical.medonline.dto.response.ServiceResponse;
import com.medical.medonline.service.ServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/service")
public class ServiceController {

    private ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ServiceResponse>> getList() {
        return ResponseEntity.ok().body(serviceService.getServices());
    }

    @PostMapping
    public ResponseEntity<ServiceResponse> create(@RequestBody ServiceRequest serviceRequest) {
        return ResponseEntity.ok().body(serviceService.createService(serviceRequest));
    }

}
