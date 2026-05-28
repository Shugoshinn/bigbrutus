package com.bigbrutus.cocinero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CocineroApplication {

	public static void main(String[] args) {
		SpringApplication.run(CocineroApplication.class, args);
	}

}
