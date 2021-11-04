package com.prgrms.broong.management.controller;

import com.prgrms.broong.management.dto.ParkCarRequestDto;
import com.prgrms.broong.management.dto.ParkCarResponseDto;
import com.prgrms.broong.management.dto.ParksInfoDto;
import com.prgrms.broong.management.service.ParkCarService;
import java.util.List;
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
@RequestMapping(path = "/api")
public class ParkCarController {

    private final ParkCarService parkCarService;

    @PostMapping(path = "/v1/broong/park-cars")
    public ResponseEntity<Long> save(@RequestBody @Valid ParkCarRequestDto parkCarRequestDto) {
        return ResponseEntity.ok(parkCarService.saveParkCar(parkCarRequestDto));
    }

    //전체 주차장 + 차량 개수 조회 <- 나중에 추가
    @GetMapping(path = "/v1/broong/park-cars")
    public ResponseEntity<List<ParksInfoDto>> getParksWithCount() {
        return ResponseEntity.ok(parkCarService.getParksWithCarCount());
    }

    @GetMapping(path = "/v1/broong/park-cars/parks/{parkId}/{carId}")
    public ResponseEntity<ParkCarResponseDto> getParkCarByParkIdAndCarId(
        @PathVariable("parkId") Long parkId,
        @PathVariable("carId") Long carId) {
        return ResponseEntity.ok(parkCarService.getParkCarByParkIdAndCarId(parkId, carId));
    }

    @GetMapping(path = "/v1/broong/park-cars/{parkId}")
    public ResponseEntity<List<ParkCarResponseDto>> getParkCarByParkId(
        @PathVariable("parkId") Long parkId) {
        return ResponseEntity.ok(parkCarService.getParkCarByParkId(parkId));
    }

    @GetMapping(path = "/v1/broong/park-cars/parks/species/{parkId}/{speciesId}")
    public ResponseEntity<List<ParkCarResponseDto>> getParkCarByParkIdAndSpeciesName(
        @PathVariable("parkId") Long parkId, @PathVariable("speciesId") Long speciesId) {
        return ResponseEntity.ok(
            parkCarService.getParkCarByParkIdAndSpeciesName(parkId, speciesId));
    }

}
