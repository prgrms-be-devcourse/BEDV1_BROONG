package com.prgrms.broong.reservation.domain;

import com.prgrms.broong.common.BaseEntity;
import com.prgrms.broong.management.park.domain.Park;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_park_id", referencedColumnName = "id")
    private Park rentPark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_park_id", referencedColumnName = "id")
    private Park returnPark;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationUser> reservationUsers = new ArrayList<>();

}
