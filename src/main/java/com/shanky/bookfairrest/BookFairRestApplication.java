package com.shanky.bookfairrest;

import com.shanky.bookfairrest.service.BootstrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BookFairRestApplication {

    @Autowired
    BootstrapService bootstrapService;

    @PostConstruct
    void init() {
        bootstrapService.initRoles();
        bootstrapService.initAdmin();
    }

    public static void main(String[] args) {
        SpringApplication.run(BookFairRestApplication.class, args);
    }

}
