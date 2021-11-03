package com.prgrms.broong.management.repository;

import com.prgrms.broong.management.domain.ParkCar;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParkCarRepository extends JpaRepository<ParkCar, Long> {


    //3. 주차장 단건 + 차량 단건 조회
    @Query("SELECT pc FROM ParkCar pc JOIN FETCH pc.car c JOIN FETCH pc.park p WHERE pc.park.id = :parkId AND pc.car.id = :carId")
    Optional<ParkCar> findParkCarByParkIdAndCarId(@Param("parkId") Long parkId,
        @Param("carId") Long carId);

    //2. 주차장 단건 + 차량 다건(페이지) 조회 -> 차량 리스트 뽑으면 됨.
    @Query("SELECT pc FROM ParkCar pc JOIN FETCH pc.car c JOIN FETCH pc.park p WHERE pc.park.id = :parkId")
    List<ParkCar> findParkCarByParkId(@Param("parkId") Long parkId);

    //5. 필터 쿼리 작성하기
    @Query("SELECT pc FROM ParkCar pc JOIN FETCH pc.car c JOIN FETCH pc.park p WHERE pc.park.id = :parkId AND pc.car.species.id = :speciesId")
    List<ParkCar> findParkCarByParkIdAndSpeciesName(@Param("parkId") Long parkId,
        @Param("speciesId") Long speciesId);

}
