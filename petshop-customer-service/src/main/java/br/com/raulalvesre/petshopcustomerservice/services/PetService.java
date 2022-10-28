package br.com.raulalvesre.petshopcustomerservice.services;

import br.com.raulalvesre.petshopcustomerservice.dtos.PetDto;
import br.com.raulalvesre.petshopcustomerservice.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    public List<PetDto> getCustomerPets(Long customerId) {
        return petRepository.findByOwner_Id(customerId).stream()
                .map(PetDto::new)
                .toList();
    }

}
