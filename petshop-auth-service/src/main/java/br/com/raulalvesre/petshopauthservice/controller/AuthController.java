package br.com.raulalvesre.petshopauthservice.controller;

import br.com.raulalvesre.petshopauthservice.dtos.LoginRequest;
import br.com.raulalvesre.petshopauthservice.dtos.JwtResponse;
import br.com.raulalvesre.petshopauthservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/customer/login")
    public ResponseEntity<JwtResponse> customerLogin(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.customerLogin(loginRequest));
    }

    @PostMapping("/veterinarian/login")
    public ResponseEntity<JwtResponse> veterinarianLogin(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.veterinarianLogin(loginRequest));
    }

    @PostMapping("/attendant/login")
    public ResponseEntity<JwtResponse> attendantLogin(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.attendantLogin(loginRequest));
    }

    @PostMapping("/admin/login")
    public ResponseEntity<JwtResponse> adminLogin(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.adminLogin(loginRequest));
    }

}
