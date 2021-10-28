package com.prgrms.broong.management.park.domain;

import com.prgrms.broong.common.BaseEntity;
import com.prgrms.broong.management.domain.ParkCar;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "park")
public class Park extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "possible_num", columnDefinition = "INT", nullable = false)
    private Integer possibleNum;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @Builder.Default
    @OneToMany(mappedBy = "park", cascade = CascadeType.ALL)
    private List<ParkCar> parkCars = new ArrayList<>();

    public void changePossibleNum(Integer possibleNum) {
        this.possibleNum = possibleNum;
    }

}
