package com.bigbrutus.cocinero;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@OpenAPIDefinition(
		info = @Info(
				title = "Cocineros Service API",
				version = "1.0.0",
				description = """
                        API REST para la gestión del personal de cocina de la pizzería.

                        Permite:
                        - Registrar nuevos cocineros y eliminarlos del sistema.
                        - Consultar el listado general y detalles por ID de forma segura utilizando el patrón DTO.
                        - Actualizar la información completa del personal de cocina.
                        - Actualizar de forma rápida y parcial (PATCH) únicamente el estado actual del cocinero.
                        - Filtrar y buscar personal específicamente por su especialidad, por su estado, o por el ID de la sucursal a la que pertenecen.
                        """,
				contact = @Contact(
						name = "Nicolas Hernandez",
						email = "nico.hernandezr@duocuc.cl"
				)
		),
		servers = {
				@Server(url = "/", description = "Servidor API Gateway")
		}
)
@SpringBootApplication
@EnableDiscoveryClient
public class CocineroApplication {

	public static void main(String[] args) {
		SpringApplication.run(CocineroApplication.class, args);
	}

}
