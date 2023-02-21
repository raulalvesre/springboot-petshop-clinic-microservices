package br.com.raulalvesre.petshopvisitsservice.services;

import br.com.raulalvesre.petshopvisitsservice.dtos.CustomerDto;
import br.com.raulalvesre.petshopvisitsservice.dtos.VeterinarianDto;
import br.com.raulalvesre.petshopvisitsservice.utils.BearerTokenWrapper;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collection;
import java.util.Set;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Service
public class VeterinarianService {

    private final WebClient webClient;
    private final BearerTokenWrapper tokenWrapper;
    private final RedisTemplate<String, VeterinarianDto> vetTemplate;
    private final RedisTemplate<String, Set<VeterinarianDto>> vetSetTemplate;

    public VeterinarianService(WebClient.Builder webClientBuilder,
                               BearerTokenWrapper tokenWrapper,
                               RedisTemplate<String, VeterinarianDto> vetTemplate,
                               RedisTemplate<String, Set<VeterinarianDto>> vetSetTemplate) {
        this.webClient = webClientBuilder.baseUrl("http://veterinarian-service/api/veterinarian")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        this.tokenWrapper = tokenWrapper;
        this.vetTemplate = vetTemplate;
        this.vetSetTemplate = vetSetTemplate;
    }

    @CircuitBreaker(name = "vetGetById", fallbackMethod = "getByIdFromCache")
    @Bulkhead(name = "vetGetById", fallbackMethod = "getByIdFromCache")
    public Mono<VeterinarianDto> getById(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .headers(x -> x.setBearerAuth(tokenWrapper.getToken()))
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        clientResponse -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Veterinarian not found")))
                .bodyToMono(VeterinarianDto.class)
                .timeout(Duration.ofSeconds(2))
                .doOnNext(resp -> {
                    HashOperations<String, String, VeterinarianDto> hashOperations = vetTemplate.opsForHash();
                    hashOperations.put("veterinarian", String.valueOf(id), resp);
                });
    }

    private Mono<VeterinarianDto> getByIdFromCache(Long id, Exception e) {
        HashOperations<String, String, VeterinarianDto> operations = vetTemplate.opsForHash();
        VeterinarianDto veterinarian = operations.get("veterinarian", String.valueOf(id));
        if (veterinarian == null) {
            throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Veterinarian service is unavailable");
        }

        return Mono.just(veterinarian);
    }

    @CircuitBreaker(name = "vetsGetByIdIn", fallbackMethod = "getByIdInFromCache")
    @Bulkhead(name = "vetsGetByIdIn", fallbackMethod = "getByIdInFromCache")
    public Mono<Set<VeterinarianDto>> getByIdIn(Collection<Long> idList) {
        String joinedList = String.join(",", idList.stream()
                .map(Object::toString)
                .toList());

        return webClient.get()
                .uri("/pageByIdInList", uriBuilder -> uriBuilder.queryParam("idList", joinedList).build())
                .headers(x -> x.setBearerAuth(tokenWrapper.getToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Set<VeterinarianDto>>() {})
                .timeout(Duration.ofSeconds(2))
                .doOnNext(resp -> {
                    HashOperations<String, String, Set<VeterinarianDto>> hashOperations = vetTemplate.opsForHash();
                    hashOperations.put("veterinarians", String.valueOf(idList.hashCode()), resp);
                });
    }

    private Mono<Set<VeterinarianDto>> getByIdInFromCache(Collection<Long> idList, Exception e) {
        HashOperations<String, String, Set<VeterinarianDto>> hashOperations = vetSetTemplate.opsForHash();
        Set<VeterinarianDto> veterinarians = hashOperations.get("veterinarians", String.valueOf(idList.hashCode()));
        if (veterinarians == null) {
            throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Veterinarians service is unavailable");
        }

        return Mono.just(veterinarians);
    }

}
