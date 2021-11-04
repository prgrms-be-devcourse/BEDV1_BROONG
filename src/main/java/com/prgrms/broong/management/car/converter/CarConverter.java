package com.prgrms.broong.management.car.converter;

import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.dto.CarRequestDto;
import com.prgrms.broong.management.car.dto.CarResponseDto;
import com.prgrms.broong.management.species.converter.SpeciesConverter;
import org.springframework.stereotype.Component;

@Component
public class CarConverter {

    private final SpeciesConverter speciesConverter;

    public CarConverter(
        SpeciesConverter speciesConverter) {
        this.speciesConverter = speciesConverter;
    }

    public Car carToEntity(CarRequestDto carRequestDto) {
        return Car.builder()
            .carNum(carRequestDto.getCarNum())
            .model(carRequestDto.getModel())
            .fuel(carRequestDto.getFuel())
            .price(carRequestDto.getPrice())
            .possiblePassengers(carRequestDto.getPossiblePassengers())
            .species(speciesConverter.speciesToEntity(carRequestDto.getSpeciesDto()))
            .build();
    }

    public Car carResponseDtoToEntity(CarResponseDto carResponseDto) {
        return Car.builder()
            .id(carResponseDto.getId())
            .carNum(carResponseDto.getCarNum())
            .model(carResponseDto.getModel())
            .fuel(carResponseDto.getFuel())
            .price(carResponseDto.getPrice())
            .possiblePassengers(carResponseDto.getPossiblePassengers())
            .species(speciesConverter.speciesToEntity(carResponseDto.getSpeciesDto()))
            .build();
    }

    public CarResponseDto carToResponseDto(Car car) {
        return CarResponseDto.builder()
            .id(car.getId())
            .carNum(car.getCarNum())
            .price(car.getPrice())
            .model(car.getModel())
            .fuel(car.getFuel())
            .possiblePassengers(car.getPossiblePassengers())
            .speciesDto(speciesConverter.speciesToDto(car.getSpecies()))
            .build();
    }

}