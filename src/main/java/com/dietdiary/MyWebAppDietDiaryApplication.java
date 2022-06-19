package com.dietdiary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class myWebAppDietDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(myWebAppDietDiaryApplication.class, args);
	}

}
