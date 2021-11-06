package com.prgrms.broong.management.car.domain;

import com.prgrms.broong.common.BaseEntity;
import com.prgrms.broong.management.car.dto.CarUpdateDto;
import com.prgrms.broong.management.species.domain.Species;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
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
@Table(name = "car")
public class Car extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "car_num", columnDefinition = "VARCHAR(100)", nullable = false)
    private String carNum;

    @Column(name = "model", columnDefinition = "VARCHAR(100)", nullable = false, updatable = false)
    private String model;

    @Min(0)
    @Column(name = "fuel", columnDefinition = "BIGINT")
    private long fuel;

    @Min(0)
    @Column(name = "price", columnDefinition = "BIGINT")
    private long price;

    @Column(name = "possible_passengers", columnDefinition = "INT", updatable = false)
    private int possiblePassengers;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "species_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_car_to_species"))
    private Species species;

    public void changeCarInfo(CarUpdateDto carUpdateDto) {
        this.carNum = carUpdateDto.getCarNum();
        this.fuel = carUpdateDto.getFuel();
        this.price = carUpdateDto.getPrice();
    }

}
