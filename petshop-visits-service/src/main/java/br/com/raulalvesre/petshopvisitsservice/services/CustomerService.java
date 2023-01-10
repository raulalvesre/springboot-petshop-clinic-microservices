package br.com.raulalvesre.petshopvisitsservice.services;

import br.com.raulalvesre.petshopvisitsservice.dtos.CustomerDto;
import br.com.raulalvesre.petshopvisitsservice.utils.BearerTokenWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collection;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@Service
public class CustomerService {

    private final WebClient webClient;
    private final BearerTokenWrapper tokenWrapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, String> redisTemplate3;
    private final RedisTemplate<String, Set<CustomerDto>> redisTemplate2;
    private final ObjectMapper objectMapper;

    public CustomerService(WebClient.Builder webClientBuilder,
                           BearerTokenWrapper tokenWrapper,
                           RedisTemplate<String, Object> redisOperations, RedisTemplate<String, String> redisTemplate3, RedisTemplate<String, Set<CustomerDto>> redisTemplate2, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl("http://customer-service/api/customer")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        this.tokenWrapper = tokenWrapper;
        this.redisTemplate = redisOperations;
        this.redisTemplate3 = redisTemplate3;
        this.redisTemplate2 = redisTemplate2;
        this.objectMapper = objectMapper;
    }

    @CircuitBreaker(name ="customerGetById", fallbackMethod = "getByIdFromCache")
    public Mono<CustomerDto> getById(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .headers(x -> x.setBearerAuth(tokenWrapper.getToken()))
                .retrieve()
                .onStatus(NOT_FOUND::equals,
                        clientResponse -> Mono.error(new ResponseStatusException(NOT_FOUND, "Customer not found")))
                .bodyToMono(CustomerDto.class)
                .timeout(Duration.ofSeconds(2))
                .doOnNext(resp -> {
                    redisTemplate.opsForHash().put("customer", id, resp);
                });
    }

    public Mono<CustomerDto> getByIdFromCache(Long id, Exception e) {
        HashOperations<String, Long, String> stringObjectObjectHashOperations = redisTemplate.opsForHash();

        String customer = stringObjectObjectHashOperations.get("customer", id);

        try {
            return Mono.just(objectMapper.readValue(customer, CustomerDto.class));
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    @CircuitBreaker(name ="customerGetById", fallbackMethod = "getByIdInFromCache")
    public Mono<Set<CustomerDto>> getByIdIn(Collection<Long> idList) {
        String joinedList = String.join(",", idList.stream()
                .map(Object::toString)
                .toList());

        return webClient.get()
                .uri("/pageByIdInList", uriBuilder -> uriBuilder.queryParam("idList", joinedList).build())
                .headers(x -> x.setBearerAuth(tokenWrapper.getToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Set<CustomerDto>>() {})
                .timeout(Duration.ofSeconds(2))
                .doOnNext(resp -> {
                    HashOperations<String, String, Set<CustomerDto>> hashOperations = redisTemplate2.opsForHash();
                    hashOperations.put("customers", String.valueOf(idList.hashCode()), resp);
                });
    }

    public Mono<Set<CustomerDto>> getByIdInFromCache(Collection<Long> idList, Exception e) {
        HashOperations<String, String, String> hashOperations = redisTemplate3.opsForHash();
        String customers = hashOperations.get("customers", String.valueOf(idList.hashCode()));
        try {
            Set<CustomerDto> customerDtos = objectMapper.readValue(customers, new TypeReference<>() {});
            return Mono.just(customerDtos);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

}
