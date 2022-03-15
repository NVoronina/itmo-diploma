package com.medical.medonline.repository;

import com.medical.medonline.entity.DoctorEntity;
import com.medical.medonline.entity.DoctorsTimetableEntity;
import com.medical.medonline.repository.impl.DoctorInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DoctorsTimetableRepository extends JpaRepository<DoctorsTimetableEntity, Long> {
    List<DoctorsTimetableEntity> getDoctorsTimetableEntitiesByDoctorAndTimeEndBeforeOrderByTimeEnd(DoctorEntity doctor, LocalDateTime dateEnd);
}
