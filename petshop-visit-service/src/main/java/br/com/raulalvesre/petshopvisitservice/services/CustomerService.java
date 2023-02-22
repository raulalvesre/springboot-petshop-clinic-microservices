package br.com.raulalvesre.petshopvisitservice.services;

import br.com.raulalvesre.petshopvisitservice.dtos.CustomerDto;
import br.com.raulalvesre.petshopvisitservice.utils.BearerTokenWrapper;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collection;
import java.util.Set;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Service
public class CustomerService {

    private final WebClient webClient;
    private final BearerTokenWrapper tokenWrapper;
    private final RedisTemplate<String, CustomerDto> customerTemplate;
    private final RedisTemplate<String, Set<CustomerDto>> customerSetTemplate;

    public CustomerService(WebClient.Builder webClientBuilder,
                           BearerTokenWrapper tokenWrapper,
                           RedisTemplate<String, CustomerDto> customerTemplate,
                           RedisTemplate<String, Set<CustomerDto>> customerSetTemplate) {
        this.webClient = webClientBuilder.baseUrl("http://customer-service/api/customer")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        this.tokenWrapper = tokenWrapper;
        this.customerTemplate = customerTemplate;
        this.customerSetTemplate = customerSetTemplate;
    }

    @CircuitBreaker(name ="customerGetById", fallbackMethod = "getByIdFromCache")
    @Bulkhead(name ="customerGetById", fallbackMethod = "getByIdFromCache")
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
                    customerTemplate.opsForHash().put("customer", String.valueOf(id), resp);
                });
    }

    private Mono<CustomerDto> getByIdFromCache(Long id, Exception e) {
        HashOperations<String, String, CustomerDto> operations = customerTemplate.opsForHash();
        CustomerDto customer = operations.get("customer", String.valueOf(id));
        if (customer == null) {
            throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Customer service is unavailable");
        }

        return Mono.just(customer);
    }

    @CircuitBreaker(name ="customersGetByIdIn", fallbackMethod = "getByIdInFromCache")
    @Bulkhead(name ="customersGetByIdIn", fallbackMethod = "getByIdInFromCache")
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
                    HashOperations<String, String, Set<CustomerDto>> hashOperations = customerSetTemplate.opsForHash();
                    hashOperations.put("customers", String.valueOf(idList.hashCode()), resp);
                });
    }

    private Mono<Set<CustomerDto>> getByIdInFromCache(Collection<Long> idList, Exception e) {
        HashOperations<String, String, Set<CustomerDto>> hashOperations = customerSetTemplate.opsForHash();
        Set<CustomerDto> customers = hashOperations.get("customers", String.valueOf(idList.hashCode()));
        if (customers == null) {
            throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Customer service is unavailable");
        }

        return Mono.just(customers);
    }

}
