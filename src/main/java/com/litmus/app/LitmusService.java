package com.litmus.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class LitmusService {

	public static void main(String[] args) {
		SpringApplication.run(LitmusService.class, args);
	}

}
