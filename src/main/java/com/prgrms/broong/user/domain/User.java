package com.prgrms.broong.user.domain;

import com.prgrms.broong.common.BaseEntity;
import com.prgrms.broong.common.BooleanToYnConverter;
import com.prgrms.broong.reservation.domain.Reservation;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "email", columnDefinition = "VARCHAR(100)", nullable = false)
    private String email;

    @Column(name = "password", columnDefinition = "VARCHAR(100)", nullable = false)
    private String password;

    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Convert(converter = BooleanToYnConverter.class)
    @Column(name = "license_info", columnDefinition = "VARCHAR(50)", nullable = false)
    private boolean licenseInfo;

    @Convert(converter = BooleanToYnConverter.class)
    @Column(name = "payment_method", columnDefinition = "VARCHAR(50)", nullable = false)
    private boolean paymentMethod;

    @Column(name = "point", columnDefinition = "INT")
    private Integer point;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

}
