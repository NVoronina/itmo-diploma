package com.medical.medonline.repository;

import com.medical.medonline.entity.AppointmentEntity;
import com.medical.medonline.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> getAppointmentEntitiesByDoctorAndTimeStartBeforeOrderByTimeStart(DoctorEntity doctor, LocalDateTime dateTime);

    @Query("from AppointmentEntity a where a.doctor = :doctor and a.timeStart >= :start and a.timeEnd <= :end")
    List<AppointmentEntity> getAppointmentsByTimeRangeAndDoctor(DoctorEntity doctor, LocalDateTime start, LocalDateTime end);
}
