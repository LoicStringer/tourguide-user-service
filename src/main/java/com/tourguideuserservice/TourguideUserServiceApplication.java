package com.tourguideuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.tourguideuserservice")
public class TourguideUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TourguideUserServiceApplication.class, args);
	}

}
