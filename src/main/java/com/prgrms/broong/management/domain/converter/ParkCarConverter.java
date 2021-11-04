package com.prgrms.broong.management.domain.converter;

import com.prgrms.broong.management.car.converter.CarConverter;
import com.prgrms.broong.management.domain.ParkCar;
import com.prgrms.broong.management.dto.ParkCarRequestDto;
import com.prgrms.broong.management.dto.ParkCarResponseDto;
import com.prgrms.broong.management.park.converter.ParkConverter;
import org.springframework.stereotype.Component;

@Component
public class ParkCarConverter {

    private final ParkConverter parkConverter;

    private final CarConverter carConverter;

    public ParkCarConverter(ParkConverter parkConverter,
        CarConverter carConverter) {
        this.parkConverter = parkConverter;
        this.carConverter = carConverter;
    }

    public ParkCar parkCarResponseToEntity(ParkCarResponseDto parkCarResponseDto) {
        return ParkCar.builder()
            .id(parkCarResponseDto.getId())
            .park(parkConverter.parkResponseToEntity(parkCarResponseDto.getParkResponseDto()))
            .car(carConverter.carResponseDtoToEntity(parkCarResponseDto.getCarResponseDto()))
            .build();
    }

    public ParkCarResponseDto parkCarToResponseDto(ParkCar parkCar) {
        return ParkCarResponseDto.builder()
            .id(parkCar.getId())
            .parkResponseDto(parkConverter.parkToResponseDto(parkCar.getPark()))
            .carResponseDto(carConverter.carToResponseDto(parkCar.getCar()))
            .build();
    }

    public ParkCar parkCarRequestToEntity(ParkCarRequestDto parkCarRequestDto) {
        return ParkCar.builder()
            .park(parkConverter.parkResponseToEntity(parkCarRequestDto.getParkResponseDto()))
            .car(carConverter.carResponseDtoToEntity(parkCarRequestDto.getCarResponseDto()))
            .build();
    }

}