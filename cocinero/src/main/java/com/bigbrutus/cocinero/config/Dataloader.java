package com.bigbrutus.cocinero.config;

import com.bigbrutus.cocinero.model.Cocinero;
import com.bigbrutus.cocinero.repository.CocineroRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Dataloader {

    @Bean
    CommandLineRunner initCocineros(CocineroRepository repository) {

        return args -> {

            if (repository.count() == 0) {

                Cocinero c1 = new Cocinero();
                c1.setNombre("Carlos");
                c1.setApellido("Diaz");
                c1.setEspecialidad("Pizza Napolitana");
                c1.setTelefono("987654321");
                c1.setIdSucursal(1L);

                Cocinero c2 = new Cocinero();
                c2.setNombre("Pedro");
                c2.setApellido("Ramirez");
                c2.setEspecialidad("Pizza Hawaiana");
                c2.setTelefono("976543210");
                c2.setIdSucursal(1L);

                repository.save(c1);
                repository.save(c2);
            }
        };
    }
}