package com.medical.medonline.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorsTimetableRequest {
    private Long doctorId;
    List<TimetableRequest> timetables;
}
