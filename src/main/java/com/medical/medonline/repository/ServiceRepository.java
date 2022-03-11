package com.medical.medonline.repository;

import com.medical.medonline.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    ServiceEntity findServiceEntityByService(String service);
}
