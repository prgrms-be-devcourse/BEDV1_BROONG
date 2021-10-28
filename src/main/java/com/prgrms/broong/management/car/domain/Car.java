package com.prgrms.broong.management.car.domain;

import com.prgrms.broong.common.BaseEntity;
import com.prgrms.broong.management.domain.ParkCar;
import com.prgrms.broong.management.species.domain.Species;
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
@Table(name = "car")
public class Car extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "car_num", columnDefinition = "VARCHAR(100)", nullable = false)
    private String carNum;

    @Column(name = "model", columnDefinition = "VARCHAR(100)", nullable = false)
    private String model;

    @Column(name = "fuel", columnDefinition = "BIGINT", nullable = false)
    private Long fuel;

    @Column(name = "price", columnDefinition = "BIGINT", nullable = false)
    private Long price;

    @Column(name = "possible_passengers", columnDefinition = "INT", nullable = false)
    private Integer possiblePassengers;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "species_id", referencedColumnName = "id")
    private Species species;

    @Builder.Default
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkCar> parkCars = new ArrayList<>();

    public void changeCarNum(String carNum) {
        this.carNum = carNum;
    }

    public void changeModel(String model) {
        this.model = model;
    }

    public void changeFuel(Long fuel) {
        this.fuel = fuel;
    }

    public void changePrice(Long price) {
        this.price = price;
    }

    public void changePossiblePassengers(Integer possiblePassengers) {
        this.possiblePassengers = possiblePassengers;
    }

}
