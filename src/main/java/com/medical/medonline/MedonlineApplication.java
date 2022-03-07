package com.medical.medonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MedonlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedonlineApplication.class, args);
	}

}
