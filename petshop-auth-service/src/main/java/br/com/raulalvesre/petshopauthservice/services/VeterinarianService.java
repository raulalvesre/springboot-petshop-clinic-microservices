package br.com.raulalvesre.petshopauthservice.services;

import br.com.raulalvesre.petshopauthservice.dtos.VeterinarianDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
public class VeterinarianService {

    private final WebClient webClient;

    public VeterinarianService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://vets-service/api/veterinarian")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<VeterinarianDto> getByEmail(String email) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/email")
                        .queryParam("email", email)
                        .build())
                .retrieve()
                .onStatus(NOT_FOUND::equals,
                        clientResponse -> Mono.error(new ResponseStatusException(UNAUTHORIZED, "Invalid username/password")))
                .bodyToMono(VeterinarianDto.class);
    }

}
