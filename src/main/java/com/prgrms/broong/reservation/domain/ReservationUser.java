package com.prgrms.broong.reservation.domain;


import com.prgrms.broong.user.domain.User;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
@Table(name = "reservation_user")
public class ReservationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "user_point", columnDefinition = "INT")
    private Integer userPoint;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status", columnDefinition = "VARCHAR(100)", nullable = false)
    private ReservationStatus reservationStatus;

    @Column(name = "fee", columnDefinition = "INT", nullable = false)
    private Integer fee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            user.getReservationUsers().remove(this);
        }
        this.user = user;
        user.getReservationUsers().add(this);
    }
}
