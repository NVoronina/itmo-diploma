package com.medical.medonline.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {
    private String timeStart;
    private Long doctorId;
    private Long patientId;
    List<Long> serviceIds;
}
