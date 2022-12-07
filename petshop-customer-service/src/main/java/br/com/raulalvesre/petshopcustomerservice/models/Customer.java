package br.com.raulalvesre.petshopcustomerservice.models;

import br.com.raulalvesre.petshopcustomerservice.dtos.CustomerForm;
import br.com.raulalvesre.petshopcustomerservice.enums.State;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String cpf;

    @Enumerated(EnumType.STRING)
    private State state;

    @NotEmpty
    private String city;

    @NotEmpty
    private String address;

    private String neighbourhood;
    private String complement;
    private String number;

    @NotEmpty
    private String cep;

    @NotEmpty
    @Size(max = 15)
    private String phone;

    @NotNull
    private LocalDate birthDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "owner", orphanRemoval = true)
    private Set<Pet> pets = new HashSet<>();

    @NotNull
    @CreatedDate
    private LocalDateTime creationDate = LocalDateTime.now();

    public Customer(Long id,
                    String name,
                    String email,
                    String password,
                    String cpf,
                    State state,
                    String city,
                    String address,
                    String neighbourhood,
                    String complement,
                    String number,
                    String cep,
                    String phone,
                    LocalDate birthDate,
                    Set<Pet> pets,
                    LocalDateTime creationDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.state = state;
        this.city = city;
        this.address = address;
        this.neighbourhood = neighbourhood;
        this.complement = complement;
        this.number = number;
        this.cep = cep;
        this.phone = phone;
        this.birthDate = birthDate;
        this.pets = pets == null ? new HashSet<>() : pets;
        this.creationDate = creationDate == null ? LocalDateTime.now() : creationDate;
    }

    public void setPets(Collection<Pet> pets) {
        if (pets != null) {
            this.pets.clear();
            this.pets.addAll(pets);
        }
    }

}
