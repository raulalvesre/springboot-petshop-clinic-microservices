package br.com.raulalvesre.petshopauthservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class UserDto {

    @NotEmpty
    private final String name;
    @NotEmpty
    private final String email;
    @NotEmpty
    private final String cpf;
    @NotEmpty
    @Size(max = 15)
    private final String phone;
    @NotNull
    private final LocalDate birthDate;


}