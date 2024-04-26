package com.globalogic.bci.ejercicioapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.globalogic.bci.ejercicioapi")
public class EjercicioApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EjercicioApiApplication.class, args);
	}

}
