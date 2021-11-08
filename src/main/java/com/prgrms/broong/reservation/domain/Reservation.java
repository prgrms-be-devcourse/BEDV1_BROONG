package com.prgrms.broong.reservation.domain;

import com.prgrms.broong.common.BaseEntity;
import com.prgrms.broong.management.domain.ParkCar;
import com.prgrms.broong.user.domain.User;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "reservation")
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "start_time", columnDefinition = "TIMESTAMP", updatable = false, nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", columnDefinition = "TIMESTAMP", updatable = false, nullable = false)
    private LocalDateTime endTime;

    @Column(name = "usage_point", columnDefinition = "INT DEFAULT 0")
    private int usagePoint;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status", columnDefinition = "VARCHAR(100)", nullable = false)
    private ReservationStatus reservationStatus;

    @Column(name = "fee", columnDefinition = "INT", nullable = false)
    private Integer fee;

    @Column(name = "is_oneway", columnDefinition = "BIT(1)", nullable = false)
    private Boolean isOneway;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_reservation_to_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "park_car_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_reservation_to_park_car"))
    private ParkCar parkCar;

    public void registerUser(User user) {
        if (Objects.nonNull(this.user)) {
            user.getReservations().remove(this);
        }
        this.user = user;
        user.getReservations().add(this);
    }

    public void changeReservationStatus(ReservationStatus status) {
        this.reservationStatus = status;
    }

    public void changeEndTime(long addHour) {
        this.endTime.plusHours(addHour);
    }

}