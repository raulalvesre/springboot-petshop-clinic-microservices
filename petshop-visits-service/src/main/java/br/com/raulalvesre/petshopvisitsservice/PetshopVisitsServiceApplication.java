package br.com.raulalvesre.petshopvisitsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PetshopVisitsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetshopVisitsServiceApplication.class, args);
	}

}
