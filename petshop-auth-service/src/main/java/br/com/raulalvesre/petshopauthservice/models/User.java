package br.com.raulalvesre.petshopauthservice.models;

import br.com.raulalvesre.petshopauthservice.dtos.UserRegistrationRequest;
import br.com.raulalvesre.petshopauthservice.enums.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Role role;

    @NotBlank
    private String name;

    @NotEmpty
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String cpf;

    @Enumerated(EnumType.STRING)
    private State state;

    @NotBlank
    private String city;

    @NotBlank
    private String address;

    private String neighbourhood;
    private String complement;
    private String number;

    @NotBlank
    private String postalCode;

    @NotBlank
    @Size(max = 15)
    private String phone;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private LocalDateTime creationDate = LocalDateTime.now();

    private boolean enabled;

    public User(UserRegistrationRequest userRegistrationRequest, Role role) {
        this.role = role;
        this.name = userRegistrationRequest.getName();
        this.email = userRegistrationRequest.getEmail();
        this.password = userRegistrationRequest.getPassword();
        this.cpf = userRegistrationRequest.getCpf();
        this.state = userRegistrationRequest.getAddress().getState();
        this.address = userRegistrationRequest.getAddress().getAddress();
        this.neighbourhood = userRegistrationRequest.getAddress().getNeighborhood();
        this.complement = userRegistrationRequest.getAddress().getComplement();
        this.number = userRegistrationRequest.getAddress().getNumber();
        this.postalCode = userRegistrationRequest.getAddress().getPostalCode();
        this.phone = userRegistrationRequest.getPhone();
        this.birthDate = userRegistrationRequest.getBirthDate();
    }

    public String getRoleName() {
        return this.role.getName();
    }

}
