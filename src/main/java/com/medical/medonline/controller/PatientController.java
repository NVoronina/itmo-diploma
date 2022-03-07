package com.medical.medonline.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/patient")
public class PatientController {

    @GetMapping("/list")
    public ResponseEntity<String> getCarsList() {

        return ResponseEntity.ok().body("test");
    }
}
