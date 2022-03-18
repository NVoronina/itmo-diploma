package com.medical.medonline.repository;

import com.medical.medonline.entity.SpecializationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationRepository extends JpaRepository<SpecializationEntity, Long>  {
    SpecializationEntity findSpecializationEntityBySpecialization(String specialization);
}
