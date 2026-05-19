package com.bigbrutus.vehiculos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class VehiculosApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehiculosApplication.class, args);
	}

}
