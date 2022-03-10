package com.medical.medonline.controller;

import com.medical.medonline.dto.response.PatientResponse;
import com.medical.medonline.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/patient")
public class PatientController {

    PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<PatientResponse>> getPatients(@RequestParam(required = true) Long doctorId) {
        return ResponseEntity.ok().body(patientService.getPatientByDoctorId(doctorId));
    }
}
