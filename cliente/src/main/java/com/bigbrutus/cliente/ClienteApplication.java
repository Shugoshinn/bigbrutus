package com.bigbrutus.cliente;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@OpenAPIDefinition(
		info = @Info(
				title = "Clientes Service API",
				version = "1.0.0",
				description = """
                        API REST para la gestión de clientes de la pizzería.

                        Permite:
                        - Registrar nuevos clientes y actualizar su información completa.
                        - Consultar el catálogo general y detalles por ID utilizando el patrón DTO.
                        - Eliminar clientes del sistema.
                        - Realizar búsquedas personalizadas de clientes por su correo electrónico o por aproximación de nombre.
                        - Filtrar exclusivamente los clientes que mantienen un estado activo.
                        - Modificar de forma rápida (PATCH) el estado de activación de un cliente (activo/inactivo).
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
public class ClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClienteApplication.class, args);
	}

}
