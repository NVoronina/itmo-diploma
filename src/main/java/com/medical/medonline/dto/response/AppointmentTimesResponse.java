package com.medical.medonline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentTimesResponse {
    private String timeStart;
    private String timeEnd;
    private List<ServiceResponse> services;
}
