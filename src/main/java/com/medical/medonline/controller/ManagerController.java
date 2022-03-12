package com.medical.medonline.controller;

import com.medical.medonline.dto.request.DoctorRequest;
import com.medical.medonline.dto.request.ManagerRequest;
import com.medical.medonline.dto.response.DoctorResponse;
import com.medical.medonline.dto.response.ManagerResponse;
import com.medical.medonline.service.DoctorService;
import com.medical.medonline.service.ManagerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/manager")
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

    @Tag(name = "Manager", description = "Delete manager by id")
    @DeleteMapping ("/{id}")
    public void deleteManager(@PathVariable long id) {
        managerService.delete(id);
    }
}
