package com.prgrms.broong.management.park.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "city_id", columnDefinition = "VARCHAR(100)", nullable = false)
    private String cityId;

    @Column(name = "town_id", columnDefinition = "VARCHAR(100)", nullable = false)
    private String townId;

    @Column(name = "location_name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String locationName;

}
