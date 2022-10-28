package br.com.raulalvesre.petshopauthservice.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class ConfirmEmailRequest {

    @NotBlank
    private String confirmationToken;

}
