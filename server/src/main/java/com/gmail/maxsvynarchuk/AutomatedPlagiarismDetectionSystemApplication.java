package com.gmail.maxsvynarchuk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class AutomatedPlagiarismDetectionSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomatedPlagiarismDetectionSystemApplication.class, args);
	}

}
