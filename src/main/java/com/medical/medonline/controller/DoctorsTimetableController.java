package com.medical.medonline.controller;

import com.medical.medonline.dto.request.DoctorsTimetableRequest;
import com.medical.medonline.dto.response.DoctorsTimetableResponse;
import com.medical.medonline.service.DoctorsTimetableService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/timetable")
@SecurityScheme(
        type= SecuritySchemeType.HTTP,
        scheme="bearer",
        name="Authorization",
        bearerFormat="JWT"
)
@SecurityRequirement(name="Authorization")
@Tag(name = "Doctors Timetable", description = "Fill doctors working day")
public class DoctorsTimetableController {

    final private DoctorsTimetableService doctorsTimetableService;

    public DoctorsTimetableController(DoctorsTimetableService doctorsTimetableService) {
        this.doctorsTimetableService = doctorsTimetableService;
    }

    @PostMapping
    public ResponseEntity<DoctorsTimetableResponse> createTimetable(@RequestBody DoctorsTimetableRequest request) {

        return ResponseEntity.ok().body(doctorsTimetableService.createTimetable(request));
    }

    @GetMapping("/list")
    public ResponseEntity<DoctorsTimetableResponse> getTimetables(@RequestParam Long doctorId) {

        return ResponseEntity.ok().body(doctorsTimetableService.getWeekTimetables(doctorId));
    }

    @DeleteMapping ("/{id}")
    public void deleteManager(@PathVariable long id) {
        doctorsTimetableService.delete(id);
    }
}
