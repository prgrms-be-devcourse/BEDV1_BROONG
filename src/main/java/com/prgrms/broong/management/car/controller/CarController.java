package com.prgrms.broong.management.car.controller;

import com.prgrms.broong.management.car.dto.CarRequestDto;
import com.prgrms.broong.management.car.dto.CarResponseDto;
import com.prgrms.broong.management.car.dto.CarUpdateDto;
import com.prgrms.broong.management.car.service.CarService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class CarController {

    private final CarService carService;

    @PostMapping(path = "/v1/broong/cars")
    public ResponseEntity<Long> save(@RequestBody @Valid CarRequestDto carRequestDto) {
        return ResponseEntity.ok(carService.saveCar(carRequestDto));
    }

    @GetMapping(path = "/v1/broong/cars/{carId}")
    public ResponseEntity<CarResponseDto> getCarById(@PathVariable("carId") Long carId) {
        return ResponseEntity.ok(carService.getCarById(carId));
    }

    @PutMapping(path = "/v1/broong/cars/{carId}")
    public ResponseEntity<Long> editCar(@PathVariable("carId") Long carId,
        @RequestBody @Valid CarUpdateDto carUpdateDto) {
        return ResponseEntity.ok(carService.editCar(carId, carUpdateDto));
    }

}
