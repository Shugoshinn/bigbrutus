package com.bigbrutus.vehiculos;

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
				title = "Vehículos Service API",
				version = "1.0.0",
				description = """
                        API REST para la gestión de vehículos de la pizzería.

                        Permite:
                        - Crear, actualizar, buscar y eliminar vehiculos
                        - Consultar listados filtrados por estado o tipo del vehiculo
                        - Realizar búsquedas específicas por patente
                        - Actualizar solo el estado del vehiculo a travéz de su ID
                        """,
				contact = @Contact(
						name = "Jacquelinne Leal",
						email = "jac.leal@duocuc.cl"
				)
		)
)
public class VehiculosApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehiculosApplication.class, args);
	}

}
