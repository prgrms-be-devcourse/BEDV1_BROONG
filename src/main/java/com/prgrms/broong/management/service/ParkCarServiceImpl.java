package com.prgrms.broong.management.service;

import com.prgrms.broong.management.domain.ParkCar;
import com.prgrms.broong.management.domain.converter.ParkCarConverter;
import com.prgrms.broong.management.dto.ParkCarRequestDto;
import com.prgrms.broong.management.dto.ParkCarResponseDto;
import com.prgrms.broong.management.dto.ParksInfoDto;
import com.prgrms.broong.management.repository.ParkCarRepository;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParkCarServiceImpl implements ParkCarService {

    private final ParkCarRepository parkCarRepository;

    private final ParkCarConverter parkCarConverter;

    @Override
    public List<ParksInfoDto> getParksWithCarCount() {
        List<ParksInfoDto> parksInfoDtos = parkCarRepository.findParksWithCarCount();
        if (parksInfoDtos.isEmpty()) {
            throw new RuntimeException("주차장에 대한 정보를 찾아올 수 없습니다.");
        }
        return parksInfoDtos;
    }

    @Override
    public ParkCarResponseDto getParkCarByParkIdAndCarId(Long parkId, Long carId) {
        return parkCarRepository.findParkCarByParkIdAndCarId(parkId, carId)
            .map(parkCarConverter::parkCarToResponseDto)
            .orElseThrow(() -> new RuntimeException(
                MessageFormat.format("해당 {0}키의 주차장의 {1} 차량을 찾을 수 없습니다.", parkId, carId)));
    }

    @Override
    public List<ParkCarResponseDto> getParkCarByParkId(Long parkId) {
        List<ParkCar> getParkCarByParkId = parkCarRepository.findParkCarByParkId(parkId);
        if (getParkCarByParkId.isEmpty()) {
            throw new RuntimeException(MessageFormat.format("해당 {0}키의 주차장을 찾을 수 없습니다.", parkId));
        }
        return getParkCarByParkId
            .stream()
            .map(parkCarConverter::parkCarToResponseDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<ParkCarResponseDto> getParkCarByParkIdAndSpeciesName(Long parkId, Long speciesId) {
        List<ParkCar> result = parkCarRepository.findParkCarByParkIdAndSpeciesName(parkId,
            speciesId);
        if (result.isEmpty()) {
            throw new RuntimeException("필터 적용이 되지 않았습니다.");
        }
        return result
            .stream()
            .map(parkCarConverter::parkCarToResponseDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long saveParkCar(ParkCarRequestDto parkCarRequestDto) {
        ParkCar parkCar = parkCarRepository.save(
            parkCarConverter.parkCarRequestToEntity(parkCarRequestDto));
        return parkCar.getId();
    }

}
