package com.medical.medonline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {

    private long id;
    private String name;
    private String surname;
    private String secondName;
    private String email;
    private SpecializationResponse specialization;
    List<ServiceResponse> services;
}
