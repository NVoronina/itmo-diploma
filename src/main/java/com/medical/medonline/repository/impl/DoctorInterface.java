package com.medical.medonline.repository.impl;

import com.medical.medonline.entity.DoctorEntity;

import java.util.List;

public interface DoctorInterface {
    List<DoctorEntity> getAllByServiceAndSpecialisation(Long specializationId, Long serviceId);
}
