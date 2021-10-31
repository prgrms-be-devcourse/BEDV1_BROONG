package com.prgrms.broong.management.park.converter;

import com.prgrms.broong.management.park.domain.Park;
import com.prgrms.broong.management.park.dto.ParkRequestDto;
import com.prgrms.broong.management.park.dto.ParkResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ParkConverter {

    public Park parkToEntity(ParkRequestDto parkRequestDto) {
        return Park.builder()
            .possibleNum(parkRequestDto.getPossibleNum())
            .location(parkRequestDto.getLocation())
            .build();
    }

    public ParkResponseDto parkToResponseDto(Park park) {
        return ParkResponseDto.builder()
            .id(park.getId())
            .possibleNum(park.getPossibleNum())
            .location(park.getLocation())
            .build();
    }

}
