package com.sangura.Errand_Service.controllers;

import com.sangura.Errand_Service.Dtos.ErrandDto;
import com.sangura.Errand_Service.Dtos.ErrandSavedDto;
import com.sangura.Errand_Service.services.ErrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/errands")
@RestController
public class ErrandController {


    private final ErrandService errandService;

    public ErrandController(ErrandService errandService) {
        this.errandService = errandService;
    }

    // ✅ CREATE
    @PostMapping("/user/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ErrandSavedDto> createErrand(@RequestBody ErrandDto errand) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(errandService.createErrand(errand));
    }

    // ✅ DELETE by ID
    @DeleteMapping("/admin/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteErrand(@PathVariable Long id) {
        return ResponseEntity.ok(errandService.deleteErrand(id));
    }

    // ✅ UPDATE by ID
    @PutMapping("/user/update/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ErrandSavedDto> updateErrand(
            @PathVariable Long id,
            @RequestBody ErrandDto errandDto
    ) {
        return ResponseEntity.ok(errandService.updateErrand(id, errandDto));
    }

    // ✅ GET ALL
    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ErrandSavedDto>> getAllErrands() {
        return ResponseEntity.ok(errandService.getAllActiveErrands());
    }

    // ✅ GET by ID
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ErrandSavedDto> getErrandById(@PathVariable Long id) {
        return ResponseEntity.ok(errandService.getErrandById(id));
    }
}
