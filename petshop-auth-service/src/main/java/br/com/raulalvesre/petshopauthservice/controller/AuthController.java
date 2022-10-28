package br.com.raulalvesre.petshopauthservice.controller;

import br.com.raulalvesre.petshopauthservice.dtos.LoginRequest;
import br.com.raulalvesre.petshopauthservice.dtos.LoginResponse;
import br.com.raulalvesre.petshopauthservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

}
