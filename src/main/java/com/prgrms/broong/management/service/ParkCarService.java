package com.prgrms.broong.management.service;


import com.prgrms.broong.management.dto.ParkCarRequestDto;
import com.prgrms.broong.management.dto.ParkCarResponseDto;
import com.prgrms.broong.management.dto.ParksInfoDto;
import java.util.List;

public interface ParkCarService {

    List<ParksInfoDto> getParksWithCarCount();

    ParkCarResponseDto getParkCarByParkIdAndCarId(Long parkId, Long carId);

    List<ParkCarResponseDto> getParkCarByParkId(Long parkId);

    List<ParkCarResponseDto> getParkCarByParkIdAndSpeciesName(Long parkId, Long speciesId);

    Long saveParkCar(ParkCarRequestDto parkCarRequestDto);

}
