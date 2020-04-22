package com.shanky.bookfairrest.serviceImplementation;

import com.shanky.bookfairrest.constants.RoleConstant;
import com.shanky.bookfairrest.domain.Role;
import com.shanky.bookfairrest.domain.User;
import com.shanky.bookfairrest.domain.UserRole;
import com.shanky.bookfairrest.repository.RoleRepository;
import com.shanky.bookfairrest.repository.UserRepository;
import com.shanky.bookfairrest.repository.UserRoleRepository;
import com.shanky.bookfairrest.service.BootstrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BootstrapServiceImp implements BootstrapService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;


    @Override
    public void initRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(RoleConstant.ROLE_ADMIN, "Role Admin"));
            roleRepository.save(new Role(RoleConstant.ROLE_AUTHOR, "Role Author"));
            roleRepository.save(new Role(RoleConstant.ROLE_BUYER, "Role Buyer"));
        }
    }

    @Override
    public void initAdmin() {
        if (userRepository.count() == 0) {
            User superAdmin = new User();
            superAdmin.setFullName("Super Admin");
            superAdmin.setUsername("superadmin");
            superAdmin.setEmail("superadmin@bookfair.com");
            superAdmin.setPassword(passwordEncoder.encode("password"));
            superAdmin.setActive(true);
            userRepository.save(superAdmin);
            UserRole userRole = new UserRole();
            userRole.setRole(roleRepository.findByAuthority(RoleConstant.ROLE_ADMIN));
            userRole.setUser(superAdmin);
            userRoleRepository.save(userRole);
        }
    }
}
