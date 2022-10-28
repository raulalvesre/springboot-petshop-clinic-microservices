package br.com.raulalvesre.petshopcustomerservice.models;

import br.com.raulalvesre.petshopcustomerservice.dtos.PetForm;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    private String name;

    @ManyToOne
    private PetType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer owner;

    private LocalDate birthDate;

    public Pet(PetForm form, PetType type, Customer owner) {
        this.name = form.getName();
        this.type = type;
        this.owner = owner;
        this.birthDate = form.getBirthDate();
    }

}
