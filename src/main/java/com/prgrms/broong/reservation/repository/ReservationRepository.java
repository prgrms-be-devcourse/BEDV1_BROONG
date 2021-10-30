package com.prgrms.broong.reservation.repository;

import com.prgrms.broong.reservation.domain.Reservation;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // userId 와 reservationId로 엮인 쿼리
    Page<Reservation> findReservationsByUserId(Long userId);

    // userId , 예약시간 으로 이미 예약된게 있는지 확인하는 쿼리
    @Query(value = "SELECT r FROM Reservation r WHERE r.user.id = :userId"
        + " AND r.startTime <= :checkTime"
        + " AND r.endTime >= :checkTime")
    Optional<Reservation> checkReservationByUserId(@Param("userId") Long userId,
        @Param("checkTime") LocalDateTime checkTime);

    // 예약시간 + carId 로 해당 시간에 예약이 가능한지 확인하는 쿼리
    @Query(value = "SELECT r FROM Reservation r WHERE r.parkCar.car.id = :carId"
        + " AND r.startTime <= :checkTime"
        + " AND r.endTime >= :checkTime")
    Optional<Reservation> possibleReservationTimeByCarId(@Param("carId") Long carId,
        @Param("checkTime") LocalDateTime checkTime);

}
