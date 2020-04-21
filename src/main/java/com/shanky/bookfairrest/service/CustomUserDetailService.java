package com.shanky.bookfairrest.service;

import com.shanky.bookfairrest.constants.RoleConstant;
import com.shanky.bookfairrest.domain.User;
import com.shanky.bookfairrest.domain.UserRole;
import com.shanky.bookfairrest.repository.RoleRepository;
import com.shanky.bookfairrest.repository.UserRepository;
import com.shanky.bookfairrest.repository.UserRoleRepository;
import com.shanky.bookfairrest.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(StringUtil.USER_NOT_FOUND);
        }
        List<UserRole> userRoles = userRoleRepository.findAllByUser(user);
        List<String> roleList = userRoles.stream().map(it -> it.getRole().getAuthority()).collect(Collectors.toList());
        List<SimpleGrantedAuthority> grantedAuthorities = roleList.stream().map(it -> new SimpleGrantedAuthority(it)).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthorities);
    }

    public void save(String fullName, String username, String email, String password) {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        UserRole userRole = new UserRole(user, roleRepository.findByAuthority(RoleConstant.ROLE_ADMIN));
        userRoleRepository.save(userRole);
    }
}
