package br.com.raulalvesre.petshopcustomerservice.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class PetForm implements Serializable {

    @NotBlank
    private String name;

    @NotNull
    private Long type_id;

    @NotNull
    private LocalDate birthDate;

}