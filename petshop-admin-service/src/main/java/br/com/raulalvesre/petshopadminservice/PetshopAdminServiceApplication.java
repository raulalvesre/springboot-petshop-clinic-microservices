package br.com.raulalvesre.petshopadminservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PetshopAdminServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetshopAdminServiceApplication.class, args);
	}

}
