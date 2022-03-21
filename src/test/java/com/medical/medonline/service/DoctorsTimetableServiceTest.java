package com.medical.medonline.service;

import com.medical.medonline.dto.request.DoctorRequest;
import com.medical.medonline.dto.request.DoctorsTimetableRequest;
import com.medical.medonline.dto.request.TimetableRequest;
import com.medical.medonline.dto.response.DoctorResponse;
import com.medical.medonline.entity.SpecializationEntity;
import com.medical.medonline.exception.NotFoundException;
import com.medical.medonline.repository.DoctorsTimetableRepository;
import com.medical.medonline.repository.SpecializationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class DoctorsTimetableServiceTest {

    @Autowired
    SpecializationRepository specializationRepository;
    @Autowired
    DoctorService doctorService;
    @Autowired
    private DoctorsTimetableService timetableService;
    @Autowired
    private DoctorsTimetableRepository timetableRepository;

    @Test
    void shouldRefillTimetable() {
        List<TimetableRequest> list = new ArrayList<>();
        list.add(new TimetableRequest(LocalDateTime.now().minusDays(2).toString(),
                LocalDateTime.now().minusDays(2).plusMinutes(60).toString()));
        list.add(new TimetableRequest(LocalDateTime.now().minusDays(3).toString(),
                LocalDateTime.now().minusDays(3).plusMinutes(60).toString()));
        SpecializationEntity specializationEntity = new SpecializationEntity();
        specializationEntity.setSpecialization("Стоматолог");
        specializationRepository.save(specializationEntity);
        DoctorRequest request = new DoctorRequest(
                specializationEntity.getId(),
                "Peter",
                "Doe",
                "Petrovich",
                "pete@test.ru",
                new ArrayList<>()
        );
        Long doctorId = doctorService.createDoctor(request).getId();

        timetableService.createTimetable(new DoctorsTimetableRequest(doctorId, list));
        int size = timetableRepository.findAll().size();
        timetableService.refillWeekTimetables();

        assertEquals(size + list.size(), timetableRepository.findAll().size());

    }

}
