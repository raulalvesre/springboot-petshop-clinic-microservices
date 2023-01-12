package br.com.raulalvesre.petshopdiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PetShopDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetShopDiscoveryApplication.class, args);
	}

}
