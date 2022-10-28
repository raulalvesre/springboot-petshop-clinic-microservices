package br.com.raulalvesre.petshopcustomerservice.controllers;

import br.com.raulalvesre.petshopcustomerservice.dtos.PetDto;
import br.com.raulalvesre.petshopcustomerservice.services.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @GetMapping("/owner/{ownerId}/pets")
    public ResponseEntity<List<PetDto>> getCustomerPets(@PathVariable("ownerId") Long ownerId) {
        return ok(petService.getCustomerPets(ownerId));
    }

}
