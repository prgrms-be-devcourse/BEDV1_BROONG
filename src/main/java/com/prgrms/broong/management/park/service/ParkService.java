package com.prgrms.broong.management.park.service;

import com.prgrms.broong.management.park.dto.ParkRequestDto;
import com.prgrms.broong.management.park.dto.ParkResponseDto;
import com.prgrms.broong.management.park.dto.ParkUpdateDto;

public interface ParkService {

    Long savePark(ParkRequestDto parkRequestDto);

    ParkResponseDto getParkById(Long parkId);

    Long editPark(Long parkId, ParkUpdateDto parkUpdateDto);

}
