package com.globalogic.bci.ejercicioapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.globalogic.bci.ejercicioapi.EjercicioApiApplication;

@SpringBootApplication
@ComponentScan({"com.globallogic.bci.ejercicioapi"})
@EntityScan("com.globallogic.bci.ejercicioapi")
@EnableJpaRepositories("com.globallogic.bci.ejercicioapi")
public class EjercicioApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EjercicioApiApplication.class, args);
	}

}
