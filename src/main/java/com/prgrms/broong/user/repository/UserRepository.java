package com.prgrms.broong.user.repository;

import com.prgrms.broong.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @EntityGraph(attributePaths = {"reservations"})
    Optional<User> findById(Long id);

}
