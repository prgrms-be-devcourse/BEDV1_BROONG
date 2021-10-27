package com.prgrms.broong.user.repository;

import com.prgrms.broong.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
