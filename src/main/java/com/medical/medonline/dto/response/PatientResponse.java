package com.medical.medonline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
    private long id;
    private String name;
    private String surname;
    private String secondName;
    private String email;
    private String contactName;
    private String contactPhone;
    private String snils;
}
