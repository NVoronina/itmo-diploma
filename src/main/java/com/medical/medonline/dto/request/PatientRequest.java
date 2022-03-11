package com.medical.medonline.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequest {
    private String name;
    private String surname;
    private String secondName;
    private String email;
    private String contactName;
    private String contactPhone;
    private String snils;
}
