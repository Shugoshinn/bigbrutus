package com.bigbrutus.producto;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@OpenAPIDefinition(
		info = @Info(
				title = "Productos Service API",
				version = "1.0.0",
				description = """
                        API REST para la gestión de productos de la pizzería.

                        Permite:
                        - Registrar nuevos productos en el catálogo
                        - Consultar el listado general y detalles por ID o categoría
                        - Actualizar información (precio, stock, disponibilidad)
                        - Eliminar productos del sistema
                        """,
				contact = @Contact(
						name = "Luis Concha",
						email = "lu.conchaa@duocuc.cl"
				)
		),
		servers = {
			@Server(url = "/", description = "Servidor API Gateway")
		}
)
@SpringBootApplication
@EnableDiscoveryClient
public class ProductoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductoApplication.class, args);
	}

}
