package com.prgrms.broong.management.car.converter;

import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.dto.CarRequestDto;
import com.prgrms.broong.management.car.dto.CarResponseDto;
import com.prgrms.broong.management.species.domain.Species;
import com.prgrms.broong.management.species.dto.SpeciesDto;
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
            .species(speciesToEntity(carRequestDto.getSpeciesDto()))
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
            .species(speciesToEntity(carResponseDto.getSpeciesDto()))
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
            .speciesDto(speciesToDto(car.getSpecies()))
            .build();
    }

    private Species speciesToEntity(SpeciesDto speciesDtoDto) {
        return Species.builder()
            .id(speciesDtoDto.getId())
            .name(speciesDtoDto.getName())
            .build();
    }

    private SpeciesDto speciesToDto(Species species) {
        return SpeciesDto.builder()
            .id(species.getId())
            .name(species.getName())
            .build();
    }

}
