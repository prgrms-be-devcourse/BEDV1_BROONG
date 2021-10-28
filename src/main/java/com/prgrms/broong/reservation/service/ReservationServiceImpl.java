package com.prgrms.broong.reservation.service;

import com.prgrms.broong.reservation.converter.ReservationConverter;
import com.prgrms.broong.reservation.domain.Reservation;
import com.prgrms.broong.reservation.dto.ReservationRequestDto;
import com.prgrms.broong.reservation.dto.ReservationResponseDto;
import com.prgrms.broong.reservation.repository.ReservationRepository;
import java.text.MessageFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;

    private final ReservationConverter converter;

    @Transactional
    @Override
    public Long saveReservation(ReservationRequestDto addReservationRequest) {
        Reservation reservation = converter.ReservationToEntity(addReservationRequest);
        return repository.save(reservation).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public ReservationResponseDto getReservation(Long reservationId) {
        return repository.findById(reservationId)
            .map(converter::ReservationToDto)
            .orElseThrow(() -> new RuntimeException(
                MessageFormat.format("해당 {0}키의 객체를 찾을 수 없습니다.", reservationId)));
    }

}
