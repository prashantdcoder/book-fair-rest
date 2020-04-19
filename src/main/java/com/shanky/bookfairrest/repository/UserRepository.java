package com.shanky.bookfairrest.repository;

import com.shanky.bookfairrest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
