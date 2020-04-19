package com.shanky.bookfairrest.repository;

import com.shanky.bookfairrest.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByAuthority(String authority);
}

