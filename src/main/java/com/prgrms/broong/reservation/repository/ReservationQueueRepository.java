package com.prgrms.broong.reservation.repository;

import com.prgrms.broong.reservation.domain.ReservationQueue;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationQueueRepository extends JpaRepository<ReservationQueue, Long> {

    @Query(value = "SELECT r FROM ReservationQueue r WHERE r.checkTime <= :checkTime")
    List<ReservationQueue> findAllReservationQueueByCheckTime(
        @Param("checkTime") LocalDateTime checkTime);

}
