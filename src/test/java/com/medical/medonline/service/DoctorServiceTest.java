package com.medical.medonline.service;

import com.medical.medonline.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class DoctorServiceTest {

    @Autowired
    private DoctorService doctorService;

    @Test
    public void getDoctorException() {
        assertThrows(NotFoundException.class, () -> doctorService.getDoctorById((long)100500));
    }

}
