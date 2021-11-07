package com.prgrms.broong.reservation.controller;


import com.prgrms.broong.reservation.dto.ReservationRequestDto;
import com.prgrms.broong.reservation.dto.ReservationResponseDto;
import com.prgrms.broong.reservation.service.ReservationService;
import com.prgrms.broong.user.dto.UserReservationCheckDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping(path = "/reservations")
    public ResponseEntity<Map<String, Long>> save(
        @RequestBody @Valid ReservationRequestDto addReservationRequest) {
        return ResponseEntity.ok(
            Map.of("reservationId", reservationService.saveReservation(addReservationRequest)));
    }

    @PatchMapping(path = "/reservations/{reservationId}")
    public ResponseEntity<Map<String, Long>> cancel(
        @PathVariable("reservationId") Long reservationId) {
        return ResponseEntity.ok(
            Map.of("reservationId", reservationService.removeReservation(reservationId)));
    }

    @GetMapping(path = "/reservations/users/{userId}")
    public ResponseEntity<Page<ReservationResponseDto>> getReservationListByUserId(
        @PathVariable("userId") Long userId,
        @RequestParam("pageNum") int pageNum,
        @RequestParam("pageSize") int pageSize) {
        return ResponseEntity.ok(
            reservationService.getReservationListByUserId(userId, pageNum, pageSize));
    }

    @GetMapping(path = "/reservations/{reservationId}")
    public ResponseEntity<ReservationResponseDto> getReservation(
        @PathVariable("reservationId") Long reservationId) {
        return ResponseEntity.ok(reservationService.getReservation(reservationId));
    }

    @GetMapping(path = "/reservations/check-reservations/{userId}")
    public ResponseEntity<Map<String, Boolean>> checkReservationByUserId(
        @PathVariable("userId") Long userId,
        @RequestParam("checkStartTime") String checkStartTime,
        @RequestParam("checkEndTime") String checkEndTime) {
        return ResponseEntity.ok(
            Map.of("checkUserReservation", reservationService.checkReservationByUserId(
                UserReservationCheckDto.builder().id(userId)
                    .checkStartTime(
                        LocalDateTime.parse(checkStartTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .checkEndTime(
                        LocalDateTime.parse(checkEndTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .build()))
        );
    }

    @GetMapping(path = "/reservations/possible-reservations/{carId}")
    public ResponseEntity<Map<String, Boolean>> possibleReservationByCarId(
        @PathVariable("carId") Long userId,
        @RequestParam("checkStartTime") String checkStartTime,
        @RequestParam("checkEndTime") String checkEndTime) {
        return ResponseEntity.ok(
            Map.of("possibleCarReservation", reservationService.possibleReservationTimeByCarId(
                userId,
                LocalDateTime.parse(checkStartTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                LocalDateTime.parse(checkEndTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
        );
    }

}
