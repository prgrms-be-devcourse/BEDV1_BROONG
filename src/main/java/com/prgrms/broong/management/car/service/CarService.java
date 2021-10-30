package com.prgrms.broong.management.car.service;

import com.prgrms.broong.management.car.dto.CarRequestDto;
import com.prgrms.broong.management.car.dto.CarResponseDto;
import com.prgrms.broong.management.car.dto.CarUpdateDto;

public interface CarService {

    Long saveCar(CarRequestDto carRequestDto);

    CarResponseDto getCarById(Long carId);

    Long editCar(Long carId, CarUpdateDto carUpdateDto);

}
