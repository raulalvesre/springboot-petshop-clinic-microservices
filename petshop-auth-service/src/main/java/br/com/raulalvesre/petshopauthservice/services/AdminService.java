package br.com.raulalvesre.petshopauthservice.services;

import br.com.raulalvesre.petshopauthservice.dtos.AdminDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service

public class AdminService {

    private final WebClient webClient;

    public AdminService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://admin-service/api/admin")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<AdminDto> getByEmail(String email) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/email")
                        .queryParam("email", email)
                        .build())
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        clientResponse -> Mono.error(new ResponseStatusException(UNAUTHORIZED, "Invalid username/password")))
                .bodyToMono(AdminDto.class);
    }

}
