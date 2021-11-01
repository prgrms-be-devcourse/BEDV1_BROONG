package com.prgrms.broong.user.repository;

import com.prgrms.broong.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u join fetch u.reservations where u.id=:id")
    Optional<User> findByIdAndReservations(@Param("id") Long id);

}
