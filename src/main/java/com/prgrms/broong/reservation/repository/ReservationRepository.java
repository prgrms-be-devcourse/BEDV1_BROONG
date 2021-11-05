package com.prgrms.broong.reservation.repository;

import com.prgrms.broong.reservation.domain.Reservation;
import com.prgrms.broong.reservation.domain.ReservationStatus;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findReservationsByUserId(Long userId, Pageable pageable);

    @Query(value = "SELECT r FROM Reservation r WHERE r.user.id = :userId"
        + " AND r.reservationStatus <> :status"
        + " AND r.startTime > :checkEndTime"
        + " AND r.endTime < :checkStartTime")
    Optional<Reservation> checkReservationByUserId(@Param("userId") Long userId,
        @Param("checkStartTime") LocalDateTime checkStartTime,
        @Param("checkEndTime") LocalDateTime checkEndTime,
        @Param("status") ReservationStatus status);

    @Query(value = "SELECT r FROM Reservation r WHERE r.parkCar.car.id = :carId"
        + " AND r.reservationStatus <> :status"
        + " AND r.startTime > :checkEndTime"
        + " AND r.endTime < :checkStartTime")
    Optional<Reservation> possibleReservationTimeByCarId(@Param("carId") Long carId,
        @Param("checkStartTime") LocalDateTime checkStartTime,
        @Param("checkEndTime") LocalDateTime checkEndTime,
        @Param("status") ReservationStatus status);

    @Query(value = "SELECT r FROM Reservation r JOIN FETCH r.user WHERE r.id = :reservationId")
    Optional<Reservation> findReservationAndUser(@Param("reservationId") Long reservationId);

}