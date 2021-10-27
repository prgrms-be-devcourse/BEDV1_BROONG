package com.prgrms.broong.reservation.repository;

import com.prgrms.broong.reservation.domain.ReservationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationUserRepository extends JpaRepository <ReservationUser, Long> {

}
