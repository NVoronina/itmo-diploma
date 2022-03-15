package com.medical.medonline.controller;

import com.medical.medonline.dto.request.ManagerRequest;
import com.medical.medonline.dto.response.ManagerResponse;
import com.medical.medonline.service.ManagerService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/manager")
@SecurityScheme(
        type= SecuritySchemeType.HTTP,
        scheme="bearer",
        name="Authorization",
        bearerFormat="JWT"
)
@SecurityRequirement(name="Authorization")
@Tag(name = "Managers", description = "Controls managers APIs")
public class ManagerController {

    final private ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ManagerResponse>> getManagers() {

        return ResponseEntity.ok().body(managerService.getManagers());
    }

    @PostMapping
    public ResponseEntity<ManagerResponse> createManager(@RequestBody ManagerRequest managerRequest) {

        return ResponseEntity.ok().body(managerService.createManager(managerRequest));
    }

    @DeleteMapping ("/{id}")
    public void deleteManager(@PathVariable long id) {
        managerService.delete(id);
    }
}
