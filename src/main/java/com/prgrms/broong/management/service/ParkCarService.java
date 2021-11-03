package com.prgrms.broong.management.service;


import com.prgrms.broong.management.dto.ParkCarRequestDto;
import com.prgrms.broong.management.dto.ParkCarResponseDto;
import java.util.List;

public interface ParkCarService {

    //1. 주차장 다건 조회(초기화면) - (여기에 있어야 하나..? park로 조회하면 끝인데.. 아 근데 있어야해 주차장에 차량이 얼마나 있는지 알아야 하니까!)
//    Page<ParksInfoDto> findParksWithCarCount(Pageable pageable);

    //3. 주차장 단건 + 차량 단건 조회
    ParkCarResponseDto getParkCarByParkIdAndCarId(Long parkId,
        Long carId);

    //2. 주차장 단건 + 차량 다건(페이지) 조회 -> 차량 리스트 뽑으면 됨.
    List<ParkCarResponseDto> getParkCarByParkId(Long parkId);

    //4. 필터를 위한 현재 있는 차종 목록 보여주기 - species에서 보여주자

    //5. 필터 쿼리 작성하기
    List<ParkCarResponseDto> getParkCarByParkIdAndSpeciesName(Long parkId, Long speciesId);

    Long saveParkCar(ParkCarRequestDto parkCarRequestDto);

}
