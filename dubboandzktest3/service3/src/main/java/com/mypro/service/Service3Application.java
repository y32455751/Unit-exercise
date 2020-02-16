package com.mypro.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mypro.service.dao")
public class Service3Application {


	public static void main(String[] args) {
		SpringApplication.run(Service3Application.class, args);
	}

}
