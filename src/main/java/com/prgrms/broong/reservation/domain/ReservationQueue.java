package com.prgrms.broong.reservation.domain;

import com.prgrms.broong.common.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "reservation_queue")
public class ReservationQueue extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "reservation_id", columnDefinition = "BIGINT")
    private Long reservationId;

    @Column(name = "check_time", columnDefinition = "TIMESTAMP", updatable = false, nullable = false)
    private LocalDateTime checkTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status", columnDefinition = "VARCHAR(100)", nullable = false)
    private ReservationStatus reservationStatus;

}
