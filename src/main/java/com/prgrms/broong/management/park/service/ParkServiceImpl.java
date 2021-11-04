package com.prgrms.broong.management.park.service;

import com.prgrms.broong.management.park.converter.ParkConverter;
import com.prgrms.broong.management.park.domain.Park;
import com.prgrms.broong.management.park.dto.ParkRequestDto;
import com.prgrms.broong.management.park.dto.ParkResponseDto;
import com.prgrms.broong.management.park.dto.ParkUpdateDto;
import com.prgrms.broong.management.park.repository.ParkRepository;
import java.text.MessageFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParkServiceImpl implements ParkService {

    private final ParkRepository parkRepository;

    private final ParkConverter parkConverter;

    @Transactional
    @Override
    public Long savePark(ParkRequestDto parkRequestDto) {
        return parkRepository.save(parkConverter.parkToEntity(parkRequestDto)).getId();
    }

    @Override
    public ParkResponseDto getParkById(Long parkId) {
        return parkRepository.findById(parkId).map(parkConverter::parkToResponseDto)
            .orElseThrow(() -> new RuntimeException(
                MessageFormat.format("해당 {0}키의 주차장을 찾을 수 없습니다.", parkId)));
    }

    @Transactional
    @Override
    public Long editPark(Long parkId, ParkUpdateDto parkUpdateDto) {
        Park getPark = parkRepository.findById(parkId)
            .orElseThrow(() -> new RuntimeException(
                MessageFormat.format("해당 {0}키의 주차장을 찾을 수 없습니다.", parkId)));
        getPark.changeParkInfo(parkUpdateDto);
        return getPark.getId();
    }

}
