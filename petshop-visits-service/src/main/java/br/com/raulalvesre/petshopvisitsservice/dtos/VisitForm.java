package br.com.raulalvesre.petshopvisitsservice.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class VisitForm {

    @NotNull
    private Long customerId;

    @NotNull
    private Long veterinarianId;

    @NotBlank
    private String description;

    private String diagnostic;

    @NotNull
    private LocalDateTime scheduledAt;

}
