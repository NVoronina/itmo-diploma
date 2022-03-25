package com.medical.medonline.service;

import com.medical.medonline.dto.request.DoctorsTimetableRequest;
import com.medical.medonline.dto.response.*;
import com.medical.medonline.entity.DoctorEntity;
import com.medical.medonline.entity.DoctorsTimetableEntity;
import com.medical.medonline.repository.DoctorsTimetableRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DoctorsTimetableService {
    private final DoctorService doctorService;
    private final DoctorsTimetableRepository doctorsTimetableRepository;
    private final ModelMapper modelMapper;

    public DoctorsTimetableService(DoctorService doctorService, DoctorsTimetableRepository doctorsTimetableRepository, ModelMapper modelMapper) {
        this.doctorService = doctorService;
        this.doctorsTimetableRepository = doctorsTimetableRepository;
        this.modelMapper = modelMapper;
    }

    public DoctorsTimetableResponse createTimetable(DoctorsTimetableRequest request) {
        DoctorEntity doctorEntity = doctorService.getById(request.getDoctorId());

        List<DoctorsTimetableEntity> list = request.getTimetables().stream()
                .map(timetableDto -> {
                    DoctorsTimetableEntity entity = new DoctorsTimetableEntity();
                    entity.setTimeStart(LocalDateTime.parse(timetableDto.getTimeStart()));
                    entity.setTimeEnd(LocalDateTime.parse(timetableDto.getTimeEnd()));
                    entity.setDoctor(doctorEntity);
                    return entity;
                })
                .toList();
        doctorsTimetableRepository.saveAll(list);
        doctorEntity.setDoctorsTimetables(list);

        return modelMapper.map(doctorEntity, DoctorsTimetableResponse.class);
    }

    public DoctorsTimetableResponse getWeekTimetablesByDoctorId(Long doctorId) {
        DoctorEntity doctorEntity = doctorService.getById(doctorId);
        return modelMapper.map(
                doctorsTimetableRepository
                        .getDoctorsTimetableEntitiesByDoctorAndTimeEndBeforeOrderByTimeEnd(
                                doctorEntity,
                                LocalDateTime.now().plusWeeks(1)
                        ),
                DoctorsTimetableResponse.class
        );
    }

    public void delete(long id) {
        DoctorsTimetableEntity timetableEntity = doctorsTimetableRepository.getById(id);
        if (timetableEntity.getDoctor() != null) {
            doctorsTimetableRepository.delete(timetableEntity);
        }
    }

    @Transactional
    public void refillWeekTimetables() {
        List<DoctorsTimetableEntity> list = doctorsTimetableRepository.getDoctorsTimetableEntitiesForRefill(
                LocalDateTime.now(),
                LocalDateTime.now().plusWeeks(1),
                LocalDateTime.now().minusWeeks(1),
                LocalDateTime.now());

        List<DoctorsTimetableEntity> result = list.stream()
                .map(timetableDto -> {
                    DoctorsTimetableEntity entity = new DoctorsTimetableEntity();
                    entity.setTimeStart(timetableDto.getTimeStart().plusWeeks(1));
                    entity.setTimeEnd(timetableDto.getTimeEnd().plusWeeks(1));
                    entity.setDoctor(timetableDto.getDoctor());
                    return entity;
                })
                .toList();
        doctorsTimetableRepository.saveAll(result);
    }

}
