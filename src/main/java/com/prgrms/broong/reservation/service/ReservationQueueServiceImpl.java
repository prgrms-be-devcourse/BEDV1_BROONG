package com.prgrms.broong.reservation.service;

import com.prgrms.broong.reservation.domain.ReservationQueue;
import com.prgrms.broong.reservation.repository.ReservationQueueRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationQueueServiceImpl implements
    ReservationQueueService {

    private final ReservationQueueRepository repository;
    private final ReservationService reservationService;

    @Transactional
    @Override
    public void editReservationQueue() {
        List<ReservationQueue> QueueAll = repository.findAllReservationQueueByCheckTime(
            LocalDateTime.now());
        for (ReservationQueue rq : QueueAll) {
            reservationService.editReservationByReservationQueue(rq.getReservationId(),
                rq.getReservationStatus());
            repository.deleteById(rq.getId());
        }
    }

}
