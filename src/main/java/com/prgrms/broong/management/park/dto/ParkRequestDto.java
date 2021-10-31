package com.prgrms.broong.management.park.dto;

import com.prgrms.broong.management.domain.ParkCar;
import com.prgrms.broong.management.park.domain.Location;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParkRequestDto {

    Integer possibleNum;

    Location location;

    List<ParkCar> parkCars;

}
