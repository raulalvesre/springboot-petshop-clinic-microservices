package br.com.raulalvesre.petshopvetsservice.controllers;

import br.com.raulalvesre.petshopvetsservice.dtos.VeterinarianAuthDto;
import br.com.raulalvesre.petshopvetsservice.dtos.VeterinarianDto;
import br.com.raulalvesre.petshopvetsservice.dtos.VeterinarianForm;
import br.com.raulalvesre.petshopvetsservice.services.VeterinarianService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/veterinarian")
@RequiredArgsConstructor
public class VetController {

    private final VeterinarianService veterinarianService;
    public class Test {
        public LocalDate test = LocalDate.now();
        public LocalDateTime test2 = LocalDateTime.now();

        public Test() {}

        public Test(LocalDate test, LocalDateTime localDateTime) {
            this.test = test;
            test2 = localDateTime;
        }

        public LocalDate getTest() {
            return test;
        }

        public void setTest(LocalDate test) {
            this.test = test;
        }

        public LocalDateTime getTest2() {
            return test2;
        }

        public void setTest2(LocalDateTime test2) {
            this.test2 = test2;
        }
    }

    @GetMapping("/teste")
    public ResponseEntity<Test> getssssId() {
        try {
            return ResponseEntity.ok(new Test());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("{id}")
    public ResponseEntity<VeterinarianDto> getById(@PathVariable("id") Long id) throws Throwable {
        return ResponseEntity.ok(veterinarianService.getById(id));
    }

    @GetMapping("/email")
    public ResponseEntity<VeterinarianAuthDto> getByEmail(@RequestParam("email") String email) throws Throwable {
        return ResponseEntity.ok(veterinarianService.getByEmail(email));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<VeterinarianDto>> getPage(@RequestParam(required = false, value = "page", defaultValue = "0") Integer page,
                                                         @RequestParam(required = false, value = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(parsePageNumber(page), size);
        return ResponseEntity.ok(veterinarianService.getPage(pageable));
    }

    private int parsePageNumber(Integer pageNumber) {
        return pageNumber < 0 ? 0 : pageNumber;
    }

    @GetMapping("/pageByIdInList")
    public ResponseEntity<Set<VeterinarianDto>> getByIdIn(@RequestParam("idList") Collection<Long> idList) {
        return ResponseEntity.ok(veterinarianService.getByIdIn(idList));
    }

    @PostMapping
    public ResponseEntity<VeterinarianDto> create(@RequestBody @Valid VeterinarianForm customerForm) {
        VeterinarianDto customerDto = veterinarianService.create(customerForm);
        UriComponents uri = UriComponentsBuilder.newInstance()
                .pathSegment("veterinarian", customerDto.getId().toString())
                .build();

        return ResponseEntity.created(uri.toUri()).body(customerDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<VeterinarianDto> update(@PathVariable("id") Long id,
                                                  @RequestBody @Valid VeterinarianForm customerForm) {
        veterinarianService.update(id, customerForm);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<VeterinarianDto> delete(@PathVariable("id") Long id) {
        veterinarianService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
