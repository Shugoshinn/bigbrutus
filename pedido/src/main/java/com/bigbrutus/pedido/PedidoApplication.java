package com.bigbrutus.pedido;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@OpenAPIDefinition(
		info = @Info(
				title = "Pedidos Service API",
				version = "1.0.0",
				description = """
                        API REST para la gestión de pedidos de la pizzería.

                        Permite:
                        - Crear, actualizar, buscar y eliminar pedidos
                        - Consultar listados filtrados por estado del pedido
                        - Realizar búsquedas específicas por nombre de cliente, repartidor o vendedor
                        - Integrar y consolidar información detallada comunicándose con los microservicios de Clientes, Sucursales, Repartidores y Vendedores
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
@EnableFeignClients
public class PedidoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PedidoApplication.class, args);
	}

}
