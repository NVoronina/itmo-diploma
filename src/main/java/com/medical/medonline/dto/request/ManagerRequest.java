package com.medical.medonline.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerRequest {
    private String name;
    private String surname;
    private String secondName;
    private String email;
}
