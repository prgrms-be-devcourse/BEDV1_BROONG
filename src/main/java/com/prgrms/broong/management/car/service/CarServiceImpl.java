package com.prgrms.broong.management.car.service;

import com.prgrms.broong.management.car.converter.CarConverter;
import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.dto.CarRequestDto;
import com.prgrms.broong.management.car.dto.CarResponseDto;
import com.prgrms.broong.management.car.dto.CarUpdateDto;
import com.prgrms.broong.management.car.repository.CarRepository;
import java.text.MessageFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final CarConverter carConverter;

    @Transactional
    @Override
    public Long saveCar(CarRequestDto carRequestDto) {
        return carRepository.save(carConverter.carToEntity(carRequestDto)).getId();
    }

    @Override
    public CarResponseDto getCarById(Long carId) {
        return carRepository.findById(carId)
            .map(carConverter::carToResponseDto)
            .orElseThrow(
                () -> new RuntimeException(MessageFormat.format("해당 {0}키의 차량을 찾을 수 없습니다.", carId)));
    }

    @Transactional
    @Override
    public Long editCar(Long carId, CarUpdateDto carUpdateDto) {
        Car getCar = carRepository.findById(carId)
            .orElseThrow(
                () -> new RuntimeException(MessageFormat.format("해당 {0}키의 차량을 찾을 수 없습니다.", carId)));
        getCar.changeCarInfo(carUpdateDto);
        return getCar.getId();
    }

}
