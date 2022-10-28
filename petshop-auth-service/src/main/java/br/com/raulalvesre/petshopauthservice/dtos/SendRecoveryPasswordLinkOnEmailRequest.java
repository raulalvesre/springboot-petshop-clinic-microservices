package br.com.raulalvesre.petshopauthservice.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@NoArgsConstructor
@Getter
@Setter
public class SendRecoveryPasswordLinkOnEmailRequest {

    @Email
    private String email;

}
