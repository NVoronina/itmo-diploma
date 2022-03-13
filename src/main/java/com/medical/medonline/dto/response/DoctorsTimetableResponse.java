package com.medical.medonline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorsTimetableResponse {
    private Long doctorId;
    List<TimetableResponse> timetables;
}
