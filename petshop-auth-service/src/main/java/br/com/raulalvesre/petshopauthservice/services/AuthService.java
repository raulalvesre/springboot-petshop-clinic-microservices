package br.com.raulalvesre.petshopauthservice.services;

import br.com.raulalvesre.petshopauthservice.dtos.CustomerDto;
import br.com.raulalvesre.petshopauthservice.dtos.JwtResponse;
import br.com.raulalvesre.petshopauthservice.dtos.LoginRequest;
import br.com.raulalvesre.petshopauthservice.dtos.VeterinarianDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtGeneratorService jwtGeneratorService;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final VeterinarianService veterinarianService;

    public JwtResponse customerLogin(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        CustomerDto customerDto = customerService.getByEmail(email).block();

        if (!passwordEncoder.matches(password, customerDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username/password");
        }

        Map<String, String> claims = Map.of("role", "ROLE_CUSTOMER");

        String token = jwtGeneratorService.generateJwt(customerDto.getEmail(), claims);
        return new JwtResponse(token);
    }

    public JwtResponse veterinarianLogin(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        VeterinarianDto veterinarianDto = veterinarianService.getByEmail(email).block();

        if (!passwordEncoder.matches(password, veterinarianDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username/password");
        }

        Map<String, String> claims = Map.of("role", "ROLE_VETERINARIAN");

        String token = jwtGeneratorService.generateJwt(email, claims);
        return new JwtResponse(token);
    }
    
    public JwtResponse attendantLogin(LoginRequest loginRequest) {
        return null;
    }

}
