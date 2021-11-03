package com.prgrms.broong.reservation.controller;


import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.reservation.dto.ReservationRequestDto;
import com.prgrms.broong.reservation.dto.ReservationResponseDto;
import com.prgrms.broong.reservation.service.ReservationService;
import com.prgrms.broong.user.dto.UserReservationCheckDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping(path = "/v1/broong/reservations")
    public ResponseEntity<Long> save(
        @RequestBody @Valid ReservationRequestDto addReservationRequest) {
        return ResponseEntity.ok(
            reservationService.saveReservation(addReservationRequest));
    }

    @PutMapping(path = "/v1/broong/reservations/{reservationId}")
    public ResponseEntity<Long> cancel(@PathVariable("reservationId") Long reservationId) {
        return ResponseEntity.ok(reservationService.removeReservation(reservationId));
    }

    @GetMapping(path = "/v1/broong/reservations/users/{userId}")
    public ResponseEntity<Page<ReservationResponseDto>> getReservationListByUserId(
        @PathVariable("userId") Long userId, Pageable pageable) {
        return ResponseEntity.ok(reservationService.getReservationListByUserId(userId, pageable));
    }

    @GetMapping(path = "/v1/broong/reservations/{reservationId}")
    public ResponseEntity<ReservationResponseDto> getReservation(
        @PathVariable("reservationId") Long reservationId) {
        return ResponseEntity.ok(reservationService.getReservation(reservationId));
    }

    @GetMapping(path = "/v1/broong/reservations/check-reservations/{userId}")
    public ResponseEntity<Boolean> checkReservationByUserId(
        @PathVariable("userId") Long userId,
        @RequestParam String checkTime) {
        return ResponseEntity.ok(reservationService.checkReservationByUserId(
            UserReservationCheckDto.builder().id(userId)
                .checkTime(LocalDateTime.parse(checkTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build(),
            ReservationStatus.CANCELD)
        );
    }

    @GetMapping(path = "/v1/broong/reservations/possible-reservations/{carId}")
    public ResponseEntity<Boolean> possibleReservationByCarId(
        @PathVariable("carId") Long userId,
        @RequestParam String checkTime) {
        return ResponseEntity.ok(reservationService.possibleReservationTimeByCarId(
            userId,
            LocalDateTime.parse(checkTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            ReservationStatus.CANCELD)
        );
    }

}
