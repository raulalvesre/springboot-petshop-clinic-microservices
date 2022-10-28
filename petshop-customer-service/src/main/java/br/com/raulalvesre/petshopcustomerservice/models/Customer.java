package br.com.raulalvesre.petshopcustomerservice.models;

import br.com.raulalvesre.petshopcustomerservice.dtos.CustomerForm;
import br.com.raulalvesre.petshopcustomerservice.enums.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private LocalDateTime creationDate = LocalDateTime.now();

    public Customer(CustomerForm customerForm) {
        this.name = customerForm.getName();
        this.email = customerForm.getEmail();
        this.cpf = customerForm.getCpf();
        this.state = customerForm.getAddress().getState();;
        this.city = customerForm.getAddress().getCity();
        this.address = customerForm.getAddress().getAddress();
        this.neighbourhood = customerForm.getAddress().getNeighbourhood();
        this.complement = customerForm.getAddress().getComplement();
        this.number = customerForm.getAddress().getNumber();
        this.cep = customerForm.getCpf();
        this.phone = customerForm.getPhone();
        this.birthDate = customerForm.getBirthDate();
    }

    public void merge(CustomerForm customerForm, Collection<Pet> pets) {
        this.name = customerForm.getName();
        this.email = customerForm.getEmail();
        this.cpf = customerForm.getCpf();
        this.state = customerForm.getAddress().getState();;
        this.city = customerForm.getAddress().getCity();
        this.address = customerForm.getAddress().getAddress();
        this.neighbourhood = customerForm.getAddress().getNeighbourhood();
        this.complement = customerForm.getAddress().getComplement();
        this.number = customerForm.getAddress().getNumber();
        this.cep = customerForm.getCpf();
        this.phone = customerForm.getPhone();
        this.birthDate = customerForm.getBirthDate();
        setPets(pets);
    }

    public void setPets(Collection<Pet> pets) {
        this.pets.clear();
        if (pets != null) {
            this.pets.addAll(pets);
        }
    }

}
