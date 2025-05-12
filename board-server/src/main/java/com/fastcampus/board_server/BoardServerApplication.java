package com.fastcampus.board_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.fastcampus.board_server.mapper")
public class BoardServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardServerApplication.class, args);
	}

}
