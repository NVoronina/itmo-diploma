package com.medical.medonline.service;

import com.medical.medonline.dto.request.DoctorsTimetableRequest;
import com.medical.medonline.dto.response.DoctorsTimetableResponse;
import com.medical.medonline.entity.DoctorEntity;
import com.medical.medonline.entity.DoctorsTimetableEntity;
import com.medical.medonline.repository.DoctorsTimetableRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DoctorsTimetableService {
    final private DoctorService doctorService;
    final private DoctorsTimetableRepository doctorsTimetableRepository;
    final private ModelMapper modelMapper;

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
                .collect(Collectors.toList());
        doctorsTimetableRepository.saveAll(list);
        doctorEntity.setDoctorsTimetables(list);

        return modelMapper.map(doctorEntity, DoctorsTimetableResponse.class);
    }

    public DoctorsTimetableResponse getTimetables(Long doctorId) {
        DoctorEntity doctorEntity = doctorService.getById(doctorId);
        return modelMapper.map(doctorEntity, DoctorsTimetableResponse.class);
    }

    public void delete(long id) {
        DoctorsTimetableEntity timetableEntity = doctorsTimetableRepository.getById(id);
        if (timetableEntity.getDoctor() != null) {
            doctorsTimetableRepository.delete(timetableEntity);
        }
    }
}
