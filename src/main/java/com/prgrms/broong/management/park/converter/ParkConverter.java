package com.prgrms.broong.management.park.converter;

import com.prgrms.broong.management.park.domain.Location;
import com.prgrms.broong.management.park.domain.Park;
import com.prgrms.broong.management.park.dto.LocationDto;
import com.prgrms.broong.management.park.dto.ParkRequestDto;
import com.prgrms.broong.management.park.dto.ParkResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ParkConverter {

    public Park parkToEntity(ParkRequestDto parkRequestDto) {
        return Park.builder()
            .possibleNum(parkRequestDto.getPossibleNum())
            .location(locationToEntity(parkRequestDto.getLocationDto()))
            .build();
    }

    public Park parkResponseToEntity(ParkResponseDto parkResponseDto) {
        return Park.builder()
            .id(parkResponseDto.getId())
            .possibleNum(parkResponseDto.getPossibleNum())
            .location(locationToEntity(parkResponseDto.getLocationDto()))
            .build();
    }

    public ParkResponseDto parkToResponseDto(Park park) {
        return ParkResponseDto.builder()
            .id(park.getId())
            .possibleNum(park.getPossibleNum())
            .locationDto(locationToDto(park.getLocation()))
            .build();
    }

    private Location locationToEntity(LocationDto locationDto) {
        return Location.builder()
            .id(locationDto.getId())
            .cityId(locationDto.getCityId())
            .townId(locationDto.getTownId())
            .locationName(locationDto.getLocationName())
            .build();
    }

    private LocationDto locationToDto(Location location) {
        return LocationDto.builder()
            .id(location.getId())
            .cityId(location.getCityId())
            .townId(location.getTownId())
            .locationName(location.getLocationName())
            .build();
    }

}