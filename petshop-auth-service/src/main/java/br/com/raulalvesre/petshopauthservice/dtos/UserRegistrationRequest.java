package br.com.raulalvesre.petshopauthservice.dtos;

import br.com.raulalvesre.petshopauthservice.validation.Password;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class UserRegistrationRequest {

    @NotBlank
    private String name;

    @Email
    private String email;

    @Password
    private String password;

    @CPF
    private String cpf;

    @Pattern(regexp = "^\\((?:[14689][1-9]|2[12478]|3[1234578]|5[1345]|7[134579])\\) (?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$",
             message = "Invalid phone number")
    private String phone;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private AddressDto address;

}
