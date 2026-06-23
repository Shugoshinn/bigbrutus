package com.bigbrutus.repartidores;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "Repartidores Service API",
				version = "1.0.0",
				description = """
                        API REST para la gestión de repartidores de la pizzería.

                        Permite:
                        - Crear, actualizar, buscar y eliminar repartidores
                        - Consultar listados detallado en su formato DTO
                        - Consultar listados filtrados por estado
                        - Realizar búsquedas específicas por ID y Tipo de Vehículo.
                        """,
				contact = @Contact(
						name = "Jacquelinne Leal",
						email = "jac.leal@duocuc.cl"
				)
		),
		servers = {
				@Server(url = "/", description = "Servidor API Gateway")
		}
)
public class RepartidoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepartidoresApplication.class, args);
	}

}
