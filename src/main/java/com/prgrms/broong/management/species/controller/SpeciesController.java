package com.prgrms.broong.management.species.controller;


import com.prgrms.broong.management.species.dto.SpeciesDto;
import com.prgrms.broong.management.species.service.SpeciesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class SpeciesController {

    private final SpeciesService speciesService;

    @GetMapping(path = "/species")
    public ResponseEntity<Page<SpeciesDto>> getAllSpecies(Pageable pageable) {
        return ResponseEntity.ok(speciesService.findAll(pageable));
    }

}
