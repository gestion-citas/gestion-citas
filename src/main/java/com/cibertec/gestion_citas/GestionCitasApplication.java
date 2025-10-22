package com.cibertec.gestion_citas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.cibertec.gestioncitas.controller",
    "com.cibertec.gestioncitas.service",
    "com.cibertec.gestioncitas.config"
})
@EnableJpaRepositories(basePackages = "com.cibertec.gestioncitas.repository")
@EntityScan(basePackages = "com.cibertec.gestioncitas.entity")
public class GestionCitasApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionCitasApplication.class, args);
    }
}
