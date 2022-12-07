package br.com.raulalvesre.petshopvisitsservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static br.com.raulalvesre.petshopvisitsservice.enums.Roles.ATTENDANT;
import static br.com.raulalvesre.petshopvisitsservice.enums.Roles.VETERINARIAN;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/visit/**").hasAnyRole(ATTENDANT.name(), VETERINARIAN.name())
                .antMatchers(HttpMethod.GET, "/visit/**/**").hasAnyRole(ATTENDANT.name(), VETERINARIAN.name())
                .antMatchers(HttpMethod.POST, "/visit/**").hasRole(ATTENDANT.name())
                .antMatchers(HttpMethod.PUT, "/visit/**").hasRole(ATTENDANT.name())
                .antMatchers(HttpMethod.DELETE, "/visit/**").hasRole(ATTENDANT.name())
                .antMatchers("/visit/**/diagnostic").hasRole(VETERINARIAN.name())
                .and().csrf().disable();

        return http.build();
    }

}
