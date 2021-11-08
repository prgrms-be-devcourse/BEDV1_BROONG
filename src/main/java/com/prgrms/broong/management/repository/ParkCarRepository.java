package com.prgrms.broong.management.repository;

import com.prgrms.broong.management.domain.ParkCar;
import com.prgrms.broong.management.dto.ParksInfoDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParkCarRepository extends JpaRepository<ParkCar, Long> {

    @Query(
        value =
            "SELECT p.id as parkId, p.possible_num as possibleNum, COUNT(pc.id) as cnt, "
                + "l.id as locationId, l.city_id as cityId, l.town_id as townId, l.location_name as locationName "
                + "FROM park p "
                + "LEFT OUTER JOIN park_car pc on pc.park_id = p.id "
                + "INNER JOIN location l on p.location_id = l.id "
                + "GROUP BY p.id "
                + "ORDER BY l.id",
        nativeQuery = true
    )
    List<ParksInfoDto> findParksWithCarCount();

    @Query("SELECT pc FROM ParkCar pc JOIN FETCH pc.car c JOIN FETCH pc.park p WHERE pc.park.id = :parkId AND pc.car.id = :carId")
    Optional<ParkCar> findParkCarByParkIdAndCarId(@Param("parkId") Long parkId,
        @Param("carId") Long carId);

    @Query("SELECT pc FROM ParkCar pc JOIN FETCH pc.car c JOIN FETCH pc.park p WHERE pc.park.id = :parkId")
    List<ParkCar> findParkCarByParkId(@Param("parkId") Long parkId);

    @Query("SELECT pc FROM ParkCar pc JOIN FETCH pc.car c JOIN FETCH pc.park p WHERE pc.park.id = :parkId AND pc.car.species.id = :speciesId")
    List<ParkCar> findParkCarByParkIdAndSpeciesName(@Param("parkId") Long parkId,
        @Param("speciesId") Long speciesId);

}
