package com.medical.medonline.repository;

import com.medical.medonline.entity.AppointmentEntity;
import com.medical.medonline.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> getAppointmentEntitiesByDoctorAndTimeStartBeforeOrderByTimeStart(DoctorEntity doctor, LocalDateTime dateTime);
}
