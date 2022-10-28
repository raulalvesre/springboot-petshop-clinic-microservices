package br.com.raulalvesre.petshopvisitsservice.services;

import br.com.raulalvesre.petshopvisitsservice.dtos.VeterinarianDto;
import br.com.raulalvesre.petshopvisitsservice.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Set;

@Service
public class VeterinarianService {

    private final WebClient webClient;

    public VeterinarianService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://vets-service/api/veterinarian")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    public Mono<VeterinarianDto> getById(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        clientResponse -> Mono.error(new NotFoundException("Veterinarian not found")))
                .bodyToMono(VeterinarianDto.class);
    }

    public Mono<Set<VeterinarianDto>> getByIdIn(Collection<Long> idList) {
        String joinedList = String.join(",", idList.stream()
                .map(Object::toString)
                .toList());

        return webClient.get()
                .uri("/pageByIdInList", uriBuilder -> uriBuilder.queryParam("idList", joinedList).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

}
