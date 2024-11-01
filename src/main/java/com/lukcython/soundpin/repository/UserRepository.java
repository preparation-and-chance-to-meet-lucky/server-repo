package com.lukcython.soundpin.repository;

import com.lukcython.soundpin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    //Email이 user의 요소가 아니게 되면서 사용하지 않음.
    Optional<User> findByEmail(String Email);
}
