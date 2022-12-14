package br.com.raulalvesre.petshopcustomerservice.dtos;

import br.com.raulalvesre.petshopcustomerservice.models.Pet;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class PetDto implements Serializable {

    private Long id;
    private String name;
    private String type;
    private LocalDate birthDate;

    public PetDto(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.type = pet.getType().getName();
        this.birthDate = pet.getBirthDate();
    }

}
