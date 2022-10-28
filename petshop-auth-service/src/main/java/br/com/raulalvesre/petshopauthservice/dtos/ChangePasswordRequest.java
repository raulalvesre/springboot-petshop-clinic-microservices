package br.com.raulalvesre.petshopauthservice.dtos;

import br.com.raulalvesre.petshopauthservice.validation.Password;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class ChangePasswordRequest {

    @NotBlank
    private String token;

    @Password
    private String newPassword;

}
