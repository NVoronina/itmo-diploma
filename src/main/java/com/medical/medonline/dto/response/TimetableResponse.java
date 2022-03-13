package com.medical.medonline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimetableResponse {
    private Long id;
    private String timeStart;
    private String timeEnd;
}
