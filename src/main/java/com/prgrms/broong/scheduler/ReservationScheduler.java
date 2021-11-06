package com.prgrms.broong.scheduler;

import com.prgrms.broong.reservation.service.ReservationQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReservationScheduler {

    private final ReservationQueueService reservationQueueService;

    @Scheduled(fixedDelay = 10000)
    @Async
    public void editReservationStatusJob() throws InterruptedException {
        reservationQueueService.editReservationQueue();
    }

}
