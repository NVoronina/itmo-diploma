package com.medical.medonline.controller;

import com.medical.medonline.dto.request.SpecializationRequest;
import com.medical.medonline.dto.response.SpecializationResponse;
import com.medical.medonline.service.SpecializationService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/specialization")
@SecurityScheme(
        type= SecuritySchemeType.HTTP,
        scheme="bearer",
        name="Authorization",
        bearerFormat="JWT"
)
@SecurityRequirement(name="Authorization")
@Tag(name = "Specializations")
public class SpecializationController {

    private final SpecializationService specializationService;

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
