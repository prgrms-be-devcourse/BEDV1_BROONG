package com.prgrms.broong.reservation.repository;

import com.prgrms.broong.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
