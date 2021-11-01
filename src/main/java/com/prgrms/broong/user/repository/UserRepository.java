package com.prgrms.broong.user.repository;

import com.prgrms.broong.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByIdAndLicenseInfo(Long id, boolean license);

    Optional<User> findByIdAndPaymentMethod(Long id, boolean payment);


}
