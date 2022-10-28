package br.com.raulalvesre.petshopvisitsservice.dtos;

import br.com.raulalvesre.petshopvisitsservice.models.Visit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class VisitMinifiedDto implements Serializable {

    private Long id;
    private Long customerId;
    private Long veterinarianId;
    private String description;
    private String diagnostic;
    private LocalDateTime scheduledAt;

    public VisitMinifiedDto(Visit visit) {
        this.id = visit.getId();
        this.customerId = visit.getCustomerId();
        this.veterinarianId = visit.getVeterinarianId();
        this.description = visit.getDescription();
        this.diagnostic = visit.getDiagnostic();
        this.scheduledAt = visit.getScheduledAt();
    }

}