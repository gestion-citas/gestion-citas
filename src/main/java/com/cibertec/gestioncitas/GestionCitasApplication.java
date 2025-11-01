package com.cibertec.gestioncitas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.cibertec.gestioncitas.repositories")
public class GestionCitasApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionCitasApplication.class, args);
    }
}
