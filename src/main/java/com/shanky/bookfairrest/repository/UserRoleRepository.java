package com.shanky.bookfairrest.repository;

import com.shanky.bookfairrest.domain.Role;
import com.shanky.bookfairrest.domain.User;
import com.shanky.bookfairrest.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByRole(Role role);

    List<UserRole> findAllByUser(User user);

}
