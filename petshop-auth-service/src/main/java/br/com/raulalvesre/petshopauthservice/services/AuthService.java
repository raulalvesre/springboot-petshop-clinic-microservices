package br.com.raulalvesre.petshopauthservice.services;

import br.com.raulalvesre.petshopauthservice.dtos.*;
import br.com.raulalvesre.petshopauthservice.exceptions.NotFoundException;
import br.com.raulalvesre.petshopauthservice.exceptions.UnprocessableEntityException;
import br.com.raulalvesre.petshopauthservice.models.EmailConfirmationToken;
import br.com.raulalvesre.petshopauthservice.models.PasswordRecoveryToken;
import br.com.raulalvesre.petshopauthservice.models.Role;
import br.com.raulalvesre.petshopauthservice.models.User;
import br.com.raulalvesre.petshopauthservice.repositories.EmailConfirmationTokenRepository;
import br.com.raulalvesre.petshopauthservice.repositories.PasswordRecoveryTokenRepository;
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
    private final RoleRepository roleRepository;
    private final PasswordRecoveryTokenRepository passwordRecoveryTokenRepository;
    private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;
    private final JwtGeneratorService jwtGeneratorService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender emailSender;

    public UserDto registerUser(UserRegistrationRequest userRegistrationRequest) {
        validateUniqueUserFields(userRegistrationRequest);

        Role role = roleRepository.getByName("STANDARD");
        User user = new User(userRegistrationRequest, role);
        userRepository.save(user);

        return new UserDto(user);
    }

    private void validateUniqueUserFields(UserRegistrationRequest userRegistrationRequest) {
        String email = userRegistrationRequest.getEmail();
        String cpf = userRegistrationRequest.getCpf();

        List<String> uniquenessErrors = new ArrayList<>();

        if (userRepository.existsByEmail(email))
            uniquenessErrors.add("Email already registered");

        if (userRepository.existsByCpf(cpf))
            uniquenessErrors.add("CPF already registered");

        if (!uniquenessErrors.isEmpty())
            throw new UnprocessableEntityException(uniquenessErrors);
    }

    private void sendEmailConfirmationTokenOnEmail(String userEmail) throws Exception {
        var user = userRepository
                .getByEmail(userEmail);

        var confirmEmailToken = UUID.randomUUID().toString();
        var emailConfirmationToken = new EmailConfirmationToken(confirmEmailToken, user);

        emailConfirmationTokenRepository.save(emailConfirmationToken);

        var msg = emailSender.createMimeMessage();
        var msgHelper = new MimeMessageHelper(msg, true);
        var msgText = "Your confirmation token is: " + confirmEmailToken;

        msgHelper.setTo(userEmail);
        msgHelper.setSubject("Raul's Petshop: Email Confirmation Token");
        msgHelper.setText(msgText, true);

        emailSender.send(msg);
    }

    public void confirmEmail(ConfirmEmailRequest req) {
        EmailConfirmationToken dbToken = emailConfirmationTokenRepository
                .findByToken(req.getConfirmationToken())
                .orElseThrow(() -> new NotFoundException("Non existent token"));

        LocalDateTime tokenDate = dbToken.getCreatedDate();
        if (tokenDate.isAfter(tokenDate.plus(2, ChronoUnit.HOURS)))
            throw new ResponseStatusException(HttpStatus.GONE, "Email confirmation token is expired");

        User user = dbToken.getOwner();
        user.setEnabled(true);

        userRepository.save(user);
    }


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

    public void sendPasswordRecoveryTokenOnEmail(SendRecoveryPasswordLinkOnEmailRequest req) throws Exception {
        String email = req.getEmail();
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("No users with email [" + email +"]"));

        String changePasswordToken = UUID.randomUUID().toString();
        PasswordRecoveryToken passwordRecoveryToken = new PasswordRecoveryToken(changePasswordToken, user);

        passwordRecoveryTokenRepository.save(passwordRecoveryToken);

        MimeMessage msg = emailSender.createMimeMessage();
        MimeMessageHelper msgHelper = new MimeMessageHelper(msg, true);
        String msgText = "Your password recovery token is: " + changePasswordToken;

        msgHelper.setTo(req.getEmail());
        msgHelper.setSubject("Raul's Petshop: Reset password token");
        msgHelper.setText(msgText, true);

        emailSender.send(msg);
    }

    public void handlePasswordChange(ChangePasswordRequest changePasswordRequest) {
        PasswordRecoveryToken dbToken = passwordRecoveryTokenRepository
                .findByToken(changePasswordRequest.getToken())
                .orElseThrow(() -> new NotFoundException("Non existent token"));

        LocalDateTime tokenDate = dbToken.getCreatedDate();
        if (tokenDate.isAfter(tokenDate.plus(2, ChronoUnit.HOURS)))
            throw new ResponseStatusException(HttpStatus.GONE, "Password recovery token is expired");

        User user = dbToken.getOwner();
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));

        userRepository.save(user);
    }

}
