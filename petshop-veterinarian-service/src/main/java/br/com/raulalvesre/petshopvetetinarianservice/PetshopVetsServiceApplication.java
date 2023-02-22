package br.com.raulalvesre.petshopvetetinarianservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PetshopVetsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetshopVetsServiceApplication.class, args);
	}

}
