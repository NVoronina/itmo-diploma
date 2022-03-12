package com.medical.medonline.controller;

import com.medical.medonline.dto.request.DoctorRequest;
import com.medical.medonline.dto.response.DoctorResponse;
import com.medical.medonline.entity.DoctorEntity;
import com.medical.medonline.service.DoctorService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/doctor")
@SecurityScheme(
        type= SecuritySchemeType.HTTP,
        scheme="bearer",
        name="Authorization",
        bearerFormat="JWT"
)
@SecurityRequirement(name="Authorization")
@Tag(name = "Doctors", description = "Controls doctors APIs")
public class DoctorController {

    private DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<DoctorResponse>> getDoctors(
            @RequestParam(required = false) Long specializationId,
            @RequestParam(required = false) Long serviceId
    ) {

        return ResponseEntity.ok().body(doctorService.getDoctors(specializationId, serviceId));
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> createDoctor(@RequestBody DoctorRequest doctor) {

        return ResponseEntity.ok().body(doctorService.createDoctor(doctor));
    }

}
