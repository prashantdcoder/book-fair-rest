package com.shanky.bookfairrest.service;

import org.springframework.stereotype.Component;

@Component
public interface BootstrapService {

    void initRoles();

    void initAdmin();
}
