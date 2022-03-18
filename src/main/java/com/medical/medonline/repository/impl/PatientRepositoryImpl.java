package com.medical.medonline.repository.impl;

import com.medical.medonline.entity.PatientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PatientRepositoryImpl implements PatientInterface {

    @Autowired
    private EntityManager em;

    public List<PatientEntity> getByDoctorId(Long doctorId) {
        Query query =  this.em
                .createNativeQuery(
                        "SELECT p.* FROM patients p " +
                                "INNER JOIN appointments a ON p.id = a.patient_id AND doctor_id = :doctorId ;", PatientEntity.class)
                .setParameter("doctorId", doctorId);

        return query.getResultList();
    }
}
