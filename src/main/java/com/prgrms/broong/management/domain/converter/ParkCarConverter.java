package com.prgrms.broong.management.domain.converter;

import com.prgrms.broong.management.car.converter.CarConverter;
import com.prgrms.broong.management.domain.ParkCar;
import com.prgrms.broong.management.dto.ParkCarResponseDto;
import com.prgrms.broong.management.park.converter.ParkConverter;
import org.springframework.stereotype.Component;

@Component
public class ParkCarConverter {

    public ParkCar parkCarResponseToEntity(ParkCarResponseDto parkCarResponseDto) {
        return ParkCar.builder()
            .id(parkCarResponseDto.getId())
            .park(new ParkConverter().parkResponseToEntity(parkCarResponseDto.getParkResponseDto()))
            .car(new CarConverter().carResponseDtoToEntity(parkCarResponseDto.getCarResponseDto()))
            .build();
    }

}
