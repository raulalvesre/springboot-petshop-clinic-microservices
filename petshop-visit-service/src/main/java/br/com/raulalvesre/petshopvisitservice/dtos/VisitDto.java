package br.com.raulalvesre.petshopvisitservice.dtos;

import br.com.raulalvesre.petshopvisitservice.models.Visit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class VisitDto implements Serializable {

    private Long id;
    private CustomerDto customer;
    private VeterinarianDto veterinarian;
    private String description;
    private String diagnostic;
    private LocalDateTime scheduledAt;

    public VisitDto(Visit visit, CustomerDto customer, VeterinarianDto veterinarian) {
        this.id = visit.getId();
        this.customer = customer;
        this.veterinarian = veterinarian;
        this.description = visit.getDescription();
        this.diagnostic = visit.getDiagnostic();
        this.scheduledAt = visit.getScheduledAt();
    }

}