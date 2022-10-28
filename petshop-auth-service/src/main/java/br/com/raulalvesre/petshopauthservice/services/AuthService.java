package br.com.raulalvesre.petshopauthservice.services;

import br.com.raulalvesre.petshopauthservice.dtos.*;
import br.com.raulalvesre.petshopauthservice.exceptions.NotFoundException;
import br.com.raulalvesre.petshopauthservice.exceptions.UnprocessableEntityException;
import br.com.raulalvesre.petshopauthservice.models.Role;
import br.com.raulalvesre.petshopauthservice.models.User;
import br.com.raulalvesre.petshopauthservice.repositories.RoleRepository;
import br.com.raulalvesre.petshopauthservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtGeneratorService jwtGeneratorService;
    private final AuthenticationManager authenticationManager;
    
    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            User user = userRepository.getByEmail(email);

            String token = jwtGeneratorService.generateToken(user.getEmail(), user.getRoleName());
            return new LoginResponse(token);
        } catch (AuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username/password");
        }
    }

}
