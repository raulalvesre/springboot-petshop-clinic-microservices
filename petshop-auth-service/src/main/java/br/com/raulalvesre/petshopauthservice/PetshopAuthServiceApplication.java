package br.com.raulalvesre.petshopauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PetshopAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetshopAuthServiceApplication.class, args);
	}

}
