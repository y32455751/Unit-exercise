package com.yangdayu.socket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.yangdayu.socket.socketgameclient.mapper")
@SpringBootApplication
@EnableCaching
public class SocketgameclientApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SocketgameclientApplication.class, args);
	}
}
