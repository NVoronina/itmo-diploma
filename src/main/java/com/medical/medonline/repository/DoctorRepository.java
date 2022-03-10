package com.medical.medonline.repository;

import com.medical.medonline.entity.DoctorEntity;
import com.medical.medonline.repository.impl.DoctorInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long>, DoctorInterface {
}
