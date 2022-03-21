package com.medical.medonline.repository;

import com.medical.medonline.entity.DoctorEntity;
import com.medical.medonline.entity.DoctorsTimetableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DoctorsTimetableRepository extends JpaRepository<DoctorsTimetableEntity, Long> {
    List<DoctorsTimetableEntity> getDoctorsTimetableEntitiesByDoctorAndTimeEndBeforeOrderByTimeEnd(DoctorEntity doctor, LocalDateTime dateEnd);

    @Query("from DoctorsTimetableEntity d1 " +
            "left join DoctorsTimetableEntity d2 on d1.doctor = d2.doctor and d2.timeEnd < :endNextWeek and d2.timeStart > :startNextWeek " +
            "where d1.timeEnd < :endThisWeek and d1.timeStart > :startThisWeek and d2 is null")
    List<DoctorsTimetableEntity> getDoctorsTimetableEntitiesForRefill(
            LocalDateTime startNextWeek,
            LocalDateTime endNextWeek,
            LocalDateTime startThisWeek,
            LocalDateTime endThisWeek
            );
}
