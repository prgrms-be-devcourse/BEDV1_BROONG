package com.prgrms.broong.management.controller;

import com.prgrms.broong.management.dto.ParkCarRequestDto;
import com.prgrms.broong.management.dto.ParkCarResponseDto;
import com.prgrms.broong.management.dto.ParksInfoDto;
import com.prgrms.broong.management.service.ParkCarService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class ParkCarController {

    private static final String PARK_CAR_ID = "parkCarId";

    private final ParkCarService parkCarService;

    @PostMapping(path = "/park-cars")
    public ResponseEntity<Map<String, Long>> save(
        @RequestBody @Valid ParkCarRequestDto parkCarRequestDto) {
        return ResponseEntity.ok(
            Map.of(PARK_CAR_ID, parkCarService.saveParkCar(parkCarRequestDto)));
    }

    @GetMapping(path = "/park-cars")
    public ResponseEntity<List<ParksInfoDto>> getParksWithCount() {
        return ResponseEntity.ok(parkCarService.getParksWithCarCount());
    }

    @GetMapping(path = "/park-cars/parks/{parkId}/cars/{carId}")
    public ResponseEntity<ParkCarResponseDto> getParkCarByParkIdAndCarId(
        @PathVariable("parkId") Long parkId,
        @PathVariable("carId") Long carId) {
        return ResponseEntity.ok(parkCarService.getParkCarByParkIdAndCarId(parkId, carId));
    }

    @GetMapping(path = "/park-cars/{parkId}")
    public ResponseEntity<List<ParkCarResponseDto>> getParkCarByParkId(
        @PathVariable("parkId") Long parkId) {
        return ResponseEntity.ok(parkCarService.getParkCarByParkId(parkId));
    }

    @GetMapping(path = "/park-cars/parks/{parkId}/species/{speciesId}")
    public ResponseEntity<List<ParkCarResponseDto>> getParkCarByParkIdAndSpeciesName(
        @PathVariable("parkId") Long parkId, @PathVariable("speciesId") Long speciesId) {
        return ResponseEntity.ok(
            parkCarService.getParkCarByParkIdAndSpeciesName(parkId, speciesId));
    }

}
