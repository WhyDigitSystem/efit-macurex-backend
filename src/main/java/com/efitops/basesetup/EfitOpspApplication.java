package com.efitops.basesetup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class EfitOpspApplication {

	public static void main(String[] args) {
		SpringApplication.run(EfitOpspApplication.class, args);
	}

}
