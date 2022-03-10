package com.medical.medonline.dto.request;

import com.medical.medonline.dto.response.ServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequest {
    private Long specializationId;
    private String name;
    private String surname;
    private String secondName;
    private String email;
    List<Integer> serviceIds;
}
