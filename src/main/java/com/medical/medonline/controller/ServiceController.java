package com.medical.medonline.controller;

import com.medical.medonline.dto.request.ServiceRequest;
import com.medical.medonline.dto.response.ServiceResponse;
import com.medical.medonline.service.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/service")
@SecurityScheme(
        type=SecuritySchemeType.HTTP,
        scheme="bearer",
        name="Authorization",
        bearerFormat="JWT"
)
@SecurityRequirement(name="Authorization")
@Tag(name = "Services")
public class ServiceController {

    final private ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("/list")
    @Operation(summary = "Get list of services in medical organization",
            description = "Get list of services in medical organization. Permitted for all users roles")
    public ResponseEntity<List<ServiceResponse>> getList() {
        return ResponseEntity.ok().body(serviceService.getServices());
    }

    @PostMapping
    @Operation(summary = "Create new service",
            description = "Create new service with time needed")
    public ResponseEntity<ServiceResponse> create(@RequestBody ServiceRequest serviceRequest) {
        return ResponseEntity.ok().body(serviceService.createService(serviceRequest));
    }

}
