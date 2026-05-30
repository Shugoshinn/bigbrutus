package com.bigbrutus.vendedor.config;

import com.bigbrutus.vendedor.model.EstadoVendedor;
import com.bigbrutus.vendedor.model.Vendedor;
import com.bigbrutus.vendedor.repository.VendedorRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initVendedores(VendedorRepository repository) {

        return args -> {

            if (repository.count() == 0) {

                Vendedor v1 = new Vendedor();
                v1.setNombre("Juan");
                v1.setApellido("Perez");
                v1.setTelefono("912345678");
                v1.setEmail("juan@pizzeria.com");
                v1.setSalario(new BigDecimal("500000"));
                v1.setEstado(EstadoVendedor.ACTIVO);
                v1.setIdSucursal(1L);

                Vendedor v2 = new Vendedor();
                v2.setNombre("Maria");
                v2.setApellido("Gonzalez");
                v2.setTelefono("923456789");
                v2.setEmail("maria@pizzeria.com");
                v2.setSalario(new BigDecimal("550000"));
                v2.setEstado(EstadoVendedor.ACTIVO);
                v2.setIdSucursal(1L);

                repository.save(v1);
                repository.save(v2);
            }
        };
    }
}