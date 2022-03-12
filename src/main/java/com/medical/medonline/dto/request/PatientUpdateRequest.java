package com.medical.medonline.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientUpdateRequest {
    @NotNull
    private long id;
    private String contactName;
    private String contactPhone;
}
