package com.prgrms.broong.user.domain;

import com.prgrms.broong.common.BaseEntity;
import com.prgrms.broong.reservation.domain.Reservation;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Pattern(regexp = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b")
    @Column(name = "email", columnDefinition = "VARCHAR(100)", nullable = false)
    private String email;

    @Column(name = "password", columnDefinition = "VARCHAR(100)", nullable = false)
    private String password;

    @Length(min = 2)
    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Column(name = "location_name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String locationName;

    @Column(name = "license_info", columnDefinition = "BIT(1)", nullable = false)
    private Boolean licenseInfo;

    @Column(name = "payment_method", columnDefinition = "BIT(1)", nullable = false)
    private Boolean paymentMethod;

    @Min(0)
    @Column(name = "point", columnDefinition = "INT DEFAULT 0")
    private int point;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    public void changePoint(Integer point) {
        this.point = point;
    }

    public void reduceUsagePoint(Integer usagePoint) {
        this.point -= usagePoint;
    }
}
