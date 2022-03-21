package com.medical.medonline.schedule;

import com.medical.medonline.service.DoctorsTimetableService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class FillTimetableSchedule {

    DoctorsTimetableService doctorsTimetableService;

    public FillTimetableSchedule(DoctorsTimetableService doctorsTimetableService) {
        this.doctorsTimetableService = doctorsTimetableService;
    }

    @Scheduled(cron = "0 0 1 * * SUN")
    public void reportCurrentData() {
        doctorsTimetableService.refillWeekTimetables();
    }
}