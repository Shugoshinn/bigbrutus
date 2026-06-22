package com.bigbrutus.sucursales;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(
		info = @Info(
				title = "Sucursales Service API",
				version = "1.0.0",
				description = """
                        API REST para la gestión de sucursales de la pizzería.

                        Permite:
                        - Crear, actualizar, buscar y eliminar sucursales
                        - Consultar listados filtrados por estado o tipo del vehiculo
                        - Realizar búsquedas específicas por ID, Comuna, Tipo y si está Activa o no.
                        """,
				contact = @Contact(
						name = "Jacquelinne Leal",
						email = "jac.leal@duocuc.cl"
				)
		)
)
public class SucursalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SucursalesApplication.class, args);
	}

}
