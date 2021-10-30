package com.prgrms.broong.management.car.converter;

import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.dto.CarRequestDto;
import com.prgrms.broong.management.car.dto.CarResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CarConverter {

    public Car carToEntity(CarRequestDto carRequestDto) {
        return Car.builder()
            .carNum(carRequestDto.getCarNum())
            .model(carRequestDto.getModel())
            .fuel(carRequestDto.getFuel())
            .price(carRequestDto.getPrice())
            .possiblePassengers(carRequestDto.getPossiblePassengers())
            .species(carRequestDto.getSpecies())
            .build();
    }

    public CarResponseDto carToResponseDto(Car car) {
        return CarResponseDto.builder()
            .id(car.getId())
            .carNum(car.getCarNum())
            .model(car.getModel())
            .model(car.getModel())
            .fuel(car.getFuel())
            .possiblePassengers(car.getPossiblePassengers())
            .species(car.getSpecies())
            .build();
    }

}
