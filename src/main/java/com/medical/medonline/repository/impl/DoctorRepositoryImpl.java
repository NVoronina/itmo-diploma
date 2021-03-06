package com.medical.medonline.repository.impl;

import com.medical.medonline.entity.DoctorEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class DoctorRepositoryImpl implements DoctorInterface {

    @Autowired
    private EntityManager em;

    public List<DoctorEntity> getAllByServiceAndSpecialisation(Long specializationId, Long serviceId) {
        StringBuilder queryStr = new StringBuilder("SELECT d.* FROM doctors d ");
        if (serviceId != null) {
            queryStr.append(" INNER JOIN doctors_services s ON d.id = s.doctor_id AND s.service_id = :serviceId");
        }
        if (specializationId != null) {
            queryStr.append(" WHERE d.specialization_id = :specializationId");
        }
        Query query =  this.em
            .createNativeQuery(
                    queryStr.toString(), DoctorEntity.class);
        if (serviceId != null) {
            query.setParameter("serviceId", serviceId);
        }
        if (specializationId != null) {
            query.setParameter("specializationId", specializationId);
        }

        return query.getResultList();
    }

}
