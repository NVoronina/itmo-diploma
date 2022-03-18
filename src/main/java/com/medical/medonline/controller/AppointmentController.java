package com.medical.medonline.controller;

import com.medical.medonline.dto.request.AppointmentRequest;
import com.medical.medonline.dto.response.AppointmentResponse;
import com.medical.medonline.dto.response.DoctorsAppointmentTimesResponse;
import com.medical.medonline.service.AppointmentService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/appointment")
@SecurityScheme(
        type= SecuritySchemeType.HTTP,
        scheme="bearer",
        name="Authorization",
        bearerFormat="JWT"
)
@SecurityRequirement(name="Authorization")
@Tag(name = "Appointments", description = "Controls doctors appointment APIs")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/list")
    public ResponseEntity<DoctorsAppointmentTimesResponse> getList(@RequestParam Long doctorId) {

        return ResponseEntity.ok().body(appointmentService.getList(doctorId));
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> create(@RequestBody AppointmentRequest request) {

        return ResponseEntity.ok().body(appointmentService.createAppointment(request));
    }

}
