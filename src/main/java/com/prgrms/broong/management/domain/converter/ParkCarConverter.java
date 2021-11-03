package com.prgrms.broong.management.domain.converter;

import com.prgrms.broong.management.car.converter.CarConverter;
import com.prgrms.broong.management.domain.ParkCar;
import com.prgrms.broong.management.dto.ParkCarResponseDto;
import com.prgrms.broong.management.park.converter.ParkConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkCarConverter {

    @Autowired
    private ParkConverter parkConverter;

    @Autowired
    private CarConverter carConverter;

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
}