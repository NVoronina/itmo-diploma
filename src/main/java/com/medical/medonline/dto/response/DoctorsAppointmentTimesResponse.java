package com.medical.medonline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorsAppointmentTimesResponse {
    private Long doctorId;
    List<AppointmentTimesResponse> appointments;
}
