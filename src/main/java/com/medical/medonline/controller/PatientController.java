package com.medical.medonline.controller;

import com.medical.medonline.dto.request.PatientRequest;
import com.medical.medonline.dto.request.PatientUpdateRequest;
import com.medical.medonline.dto.response.PatientResponse;
import com.medical.medonline.service.PatientService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/patient")
@SecurityScheme(
        type= SecuritySchemeType.HTTP,
        scheme="bearer",
        name="Authorization",
        bearerFormat="JWT"
)
@SecurityRequirement(name="Authorization")
@Tag(name = "Patients", description = "Controls patients APIs")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<PatientResponse>> getPatients(@RequestParam(required = true) Long doctorId) {
        return ResponseEntity.ok().body(patientService.getPatientByDoctorId(doctorId));
    }

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@RequestBody PatientRequest patient) {

        return ResponseEntity.ok().body(patientService.createPatient(patient));
    }

    @PutMapping
    public ResponseEntity<PatientResponse> updatePatient(@RequestBody PatientUpdateRequest patient) {
        return ResponseEntity.ok().body(patientService.updatePatient(patient));
    }
}
