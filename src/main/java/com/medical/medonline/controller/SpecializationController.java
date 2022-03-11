package com.medical.medonline.controller;

import com.medical.medonline.dto.request.ServiceRequest;
import com.medical.medonline.dto.request.SpecializationRequest;
import com.medical.medonline.dto.response.DoctorResponse;
import com.medical.medonline.dto.response.ServiceResponse;
import com.medical.medonline.dto.response.SpecializationResponse;
import com.medical.medonline.service.ServiceService;
import com.medical.medonline.service.SpecializationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/specialization")
public class SpecializationController {

    private SpecializationService specializationService;

    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<SpecializationResponse>> getList() {
        return ResponseEntity.ok().body(specializationService.getSpecializations());
    }

    @PostMapping
    public ResponseEntity<SpecializationResponse> create(@RequestBody SpecializationRequest specializationRequest) {
        return ResponseEntity.ok().body(specializationService.createSpecialization(specializationRequest));
    }
}
