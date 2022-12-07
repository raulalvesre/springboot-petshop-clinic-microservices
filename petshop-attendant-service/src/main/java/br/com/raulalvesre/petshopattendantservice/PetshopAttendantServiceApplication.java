package br.com.raulalvesre.petshopattendantservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PetshopAttendantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetshopAttendantServiceApplication.class, args);
	}

}
