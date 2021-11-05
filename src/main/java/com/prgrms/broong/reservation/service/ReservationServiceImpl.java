package com.prgrms.broong.reservation.service;

import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.repository.CarRepository;
import com.prgrms.broong.reservation.converter.ReservationConverter;
import com.prgrms.broong.reservation.domain.Reservation;
import com.prgrms.broong.reservation.domain.ReservationQueue;
import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.reservation.dto.ReservationRequestDto;
import com.prgrms.broong.reservation.dto.ReservationResponseDto;
import com.prgrms.broong.reservation.repository.ReservationQueueRepository;
import com.prgrms.broong.reservation.repository.ReservationRepository;
import com.prgrms.broong.user.domain.User;
import com.prgrms.broong.user.dto.UserReservationCheckDto;
import com.prgrms.broong.user.repository.UserRepository;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationServiceImpl implements ReservationService {

    private static final int ZERO_COUNT = 0;
    private static final int TEN_PERCENT = 10;
    private static final Long ADD_HOUR = 2L;

    private final ReservationRepository repository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final ReservationConverter converter;
    private final ReservationQueueRepository reservationQueueRepository;

    @Transactional
    @Override
    public Long saveReservation(ReservationRequestDto addReservationRequest) {
        Reservation reservation = converter.ReservationToEntity(addReservationRequest);
        Car car = carRepository.findById(
            addReservationRequest.getParkCarResponseDto().getCarResponseDto().getId()).orElseThrow(
            () -> new RuntimeException(MessageFormat.format("해당 {0}키의 자동차를 찾을 수 없습니다.",
                addReservationRequest.getParkCarResponseDto().getCarResponseDto().getId())));
        User getUser = userRepository.findById(addReservationRequest.getUserResponseDto().getId())
            .orElseThrow(() -> new RuntimeException(MessageFormat.format("해당 {0}키의 사용자를 찾을 수 없습니다.",
                addReservationRequest.getUserResponseDto().getId())));

        checkReservationByUserId(UserReservationCheckDto.builder().id(getUser.getId())
            .checkStartTime(addReservationRequest.getStartTime())
            .checkEndTime(addReservationRequest.getEndTime()).build());

        possibleReservationTimeByCarId(car.getId(), addReservationRequest.getStartTime(),
            addReservationRequest.getEndTime());

        if (!getUser.getLicenseInfo()) {
            throw new RuntimeException(
                MessageFormat.format("사용자:{0}는 면허를 등록해 주세요",
                    getUser.getId()));
        }
        if (!getUser.getPaymentMethod()) {
            throw new RuntimeException(
                MessageFormat.format("사용자:{0}는 결제수단을 등록해 주세요",
                    getUser.getId()));
        }
        if (!reservation.getIsOneway()) {
            reservation.changeEndTime(ADD_HOUR);
        }
        getUser.reduceUsagePoint(reservation.getUsagePoint());
        repository.save(reservation);
        ReservationQueue usingReservationQueue = ReservationQueue.builder()
            .reservationId(reservation.getId()).reservationStatus(ReservationStatus.PROCEED)
            .checkTime(reservation.getStartTime()).build();
        ReservationQueue returnReservationQueue = ReservationQueue.builder()
            .reservationId(reservation.getId()).reservationStatus(ReservationStatus.COMPLETE)
            .checkTime(reservation.getEndTime()).build();
        reservationQueueRepository.save(usingReservationQueue);
        reservationQueueRepository.save(returnReservationQueue);
        return repository.save(reservation).getId();
    }

    @Override
    public ReservationResponseDto getReservation(Long reservationId) {
        return repository.findById(reservationId)
            .map(converter::ReservationToResponseDto)
            .orElseThrow(() -> new RuntimeException(
                MessageFormat.format("해당 {0}키의 객체를 찾을 수 없습니다.", reservationId)));
    }

    @Override
    public Page<ReservationResponseDto> getReservationListByUserId(Long userId, Pageable pageable) {
        return repository.findReservationsByUserId(userId, pageable)
            .map(converter::ReservationToResponseDto);
    }

    @Override
    public boolean checkReservationByUserId(UserReservationCheckDto userReservationCheckDto) {
        long reservationCount = repository.checkReservationByUserId(userReservationCheckDto.getId(),
            userReservationCheckDto.getCheckStartTime(), userReservationCheckDto.getCheckEndTime(),
            ReservationStatus.CANCEL).stream().count();
        if (reservationCount != ZERO_COUNT) {
            throw new RuntimeException(
                MessageFormat.format("사용자:{0}키는 동일한 시간대에 예약이 존재합니다.",
                    userReservationCheckDto.getId()));
        }
        return true;
    }

    @Override
    public boolean possibleReservationTimeByCarId(Long carId, LocalDateTime checkStartTime,
        LocalDateTime checkEndTime) {
        long reservationCount = repository.possibleReservationTimeByCarId(carId,
            checkStartTime, checkEndTime, ReservationStatus.CANCEL).stream().count();
        if (reservationCount != ZERO_COUNT) {
            throw new RuntimeException(
                MessageFormat.format("자동차:{0}키는 동일한 시간대에 예약이 존재합니다.", carId));
        }
        return true;
    }

    @Transactional
    @Override
    public Long removeReservation(Long reservationId) {
        Reservation getReservation = repository.findReservationAndUser(reservationId)
            .orElseThrow(() -> new RuntimeException(
                MessageFormat.format("해당 {0}키의 객체를 찾을 수 없습니다.", reservationId)));
        getReservation.changeReservationStatus(ReservationStatus.CANCEL);
        getReservation.getUser()
            .changePoint(getReservation.getUsagePoint() + getReservation.getUser().getPoint());
        return getReservation.getId();
    }

    @Transactional
    @Override
    public void editReservationByReservationQueue(Long reservationId,
        ReservationStatus changeStatusValue) {
        Reservation getReservation = repository.findReservationAndUser(reservationId)
            .orElseThrow(() -> new RuntimeException(
                MessageFormat.format("해당 {0}키의 객체를 찾을 수 없습니다.", reservationId)));
        if (changeStatusValue.equals(ReservationStatus.COMPLETE)) {
            getReservation.getUser()
                .changePoint(
                    getReservation.getUser().getPoint() + getReservation.getFee() % TEN_PERCENT);
        }
        getReservation.changeReservationStatus(changeStatusValue);
    }

}