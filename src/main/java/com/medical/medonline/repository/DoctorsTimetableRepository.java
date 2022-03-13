package com.medical.medonline.repository;

import com.medical.medonline.entity.DoctorsTimetableEntity;
import com.medical.medonline.repository.impl.DoctorInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorsTimetableRepository extends JpaRepository<DoctorsTimetableEntity, Long> {
}
