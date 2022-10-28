package br.com.raulalvesre.petshopcustomerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PetshopCustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetshopCustomerServiceApplication.class, args);
	}

}
