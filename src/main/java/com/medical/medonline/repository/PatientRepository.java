package com.medical.medonline.repository;

import com.medical.medonline.entity.PatientEntity;
import com.medical.medonline.repository.impl.PatientInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long>, PatientInterface {
}
