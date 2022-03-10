package com.medical.medonline.repository.impl;

import com.medical.medonline.entity.PatientEntity;

import java.util.List;

public interface PatientInterface {
    List<PatientEntity> getByDoctorId(Long doctorId);
}
