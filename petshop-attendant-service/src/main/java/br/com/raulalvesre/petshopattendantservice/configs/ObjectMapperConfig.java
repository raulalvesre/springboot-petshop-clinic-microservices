package br.com.raulalvesre.petshopattendantservice.configs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        Map<Class<?>, JsonSerializer<?>> serializers = Map.of(LocalDate.class, new LocalDateSerializer(ISO_LOCAL_DATE),
                LocalDateTime.class, new LocalDateTimeSerializer(ISO_LOCAL_DATE_TIME));
        Map<Class<?>, JsonDeserializer<?>> deserializers = Map.of(LocalDate.class, new LocalDateDeserializer(ISO_LOCAL_DATE),
                LocalDateTime.class, new LocalDateTimeDeserializer(ISO_LOCAL_DATE_TIME));

        return builder -> builder.serializationInclusion(JsonInclude.Include.NON_NULL)
                .serializersByType(serializers)
                .deserializersByType(deserializers);
    }

}
