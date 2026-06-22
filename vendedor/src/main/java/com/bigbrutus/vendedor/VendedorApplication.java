package com.bigbrutus.vendedor;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@OpenAPIDefinition(
		info = @Info(
				title = "Vendedores Service API",
				version = "1.0.0",
				description = """
                        API REST para la gestión de vendedores de la pizzería.

                        Permite:
                        - Registrar nuevos vendedores y eliminarlos del sistema.
                        - Consultar el listado general y obtener detalles por ID utilizando el patrón DTO.
                        - Actualizar la información completa del perfil del vendedor.
                        - Realizar búsquedas específicas filtrando por correo electrónico, estado o la sucursal a la que están asignados.
                        - Actualizar de forma rápida y parcial (PATCH) únicamente el estado actual del vendedor.
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
public class VendedorApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendedorApplication.class, args);
	}

}
