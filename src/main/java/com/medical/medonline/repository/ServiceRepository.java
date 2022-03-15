package com.medical.medonline.repository;

import com.medical.medonline.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    ServiceEntity findServiceEntityByService(String service);

    @Query("from ServiceEntity s where s.id IN(:serviceIds)")
    Set<ServiceEntity> findAllByServiceIds(List<Long> serviceIds);
}
