package com.shanky.bookfairrest;

import com.shanky.bookfairrest.constants.RoleConstant;
import com.shanky.bookfairrest.domain.Role;
import com.shanky.bookfairrest.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BookFairRestApplication {

    @Autowired
    RoleRepository roleRepository;

    @PostConstruct
    void init() {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(RoleConstant.ROLE_ADMIN, "Role Admin"));
            roleRepository.save(new Role(RoleConstant.ROLE_AUTHOR, "Role Author"));
            roleRepository.save(new Role(RoleConstant.ROLE_READER, "Role Reader"));
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(BookFairRestApplication.class, args);
    }

}
