package com.shanky.bookfairrest;

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
        if (roleRepository.findByAuthority("ADMIN") == null) {
            Role role = new Role("ADMIN", "Role Admin");
            roleRepository.save(role);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(BookFairRestApplication.class, args);
    }

}
