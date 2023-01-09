package br.com.raulalvesre.petshopvisitsservice.config;

import aj.org.objectweb.asm.TypeReference;
import br.com.raulalvesre.petshopvisitsservice.dtos.CustomerDto;
import br.com.raulalvesre.petshopvisitsservice.services.CustomerService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Set;

@Configuration
@EnableCaching
public class RedisConfig {

    Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;


    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        RedisConfiguration redisConfiguration = new RedisStandaloneConfiguration(host, port);
        return new LettuceConnectionFactory(redisConfiguration);
    }

    @Bean
    public ReactiveRedisOperations<String, CustomerDto> redisCustomerDtoReactiveOperations(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory, ObjectMapper objectMapper) {
        Jackson2JsonRedisSerializer<CustomerDto> serializer = new Jackson2JsonRedisSerializer<>(CustomerDto.class);
        serializer.setObjectMapper(objectMapper);

        RedisSerializationContext.RedisSerializationContextBuilder<String, CustomerDto> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, CustomerDto> context = builder
                .value(serializer)
                .hashKey(serializer)
                .hashValue(serializer)
                .build();

        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, context);
    }

}
