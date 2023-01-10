package br.com.raulalvesre.petshopadminservice.controllers;

import br.com.raulalvesre.petshopadminservice.dtos.*;
import br.com.raulalvesre.petshopadminservice.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("{id}")
    public ResponseEntity<AdminDto> getById(@PathVariable("id") Long id) throws Throwable {
        return ResponseEntity.ok(adminService.getById(id));
    }

    @GetMapping("/email")
    public ResponseEntity<AdminAuthDto> getByEmail(@RequestParam("email") String email) throws Throwable {
        return ResponseEntity.ok(adminService.getByEmail(email));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<AdminDto>> getPage(@RequestParam(required = false, value = "page", defaultValue = "0") Integer page,
                                                      @RequestParam(required = false, value = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(parsePageNumber(page), size);
        return ResponseEntity.ok(adminService.getPage(pageable));
    }

    private int parsePageNumber(Integer pageNumber) {
        return pageNumber < 0 ? 0 : pageNumber;
    }

    @GetMapping("/pageByIdInList")
    public ResponseEntity<Set<AdminDto>> getByIdIn(@RequestParam("idList") Collection<Long> idList) {
        return ResponseEntity.ok(adminService.getByIdIn(idList));
    }

    @PostMapping
    public ResponseEntity<AdminDto> create(@RequestBody @Valid AdminForm AdminForm) {
        AdminDto customerDto = adminService.create(AdminForm);
        UriComponents uri = UriComponentsBuilder.newInstance()
                .pathSegment("admin", customerDto.getId().toString())
                .build();

        return ResponseEntity.created(uri.toUri()).body(customerDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id,
                                       @RequestBody @Valid AdminForm AdminForm) {
        adminService.update(id, AdminForm);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AdminDto> delete(@PathVariable("id") Long id) {
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
