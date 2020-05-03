package com.shanky.bookfairrest.service;

import com.shanky.bookfairrest.DTO.ResponseDTO;
import com.shanky.bookfairrest.constants.RoleConstant;
import com.shanky.bookfairrest.domain.User;
import com.shanky.bookfairrest.domain.UserRole;
import com.shanky.bookfairrest.repository.RoleRepository;
import com.shanky.bookfairrest.repository.UserRepository;
import com.shanky.bookfairrest.repository.UserRoleRepository;
import com.shanky.bookfairrest.utils.AppUtil;
import com.shanky.bookfairrest.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    @Autowired
    private EntityManager entityManager;

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

    public User findByEmail(String email) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot).where(builder.equal(userRoot.get("email"), email));
        TypedQuery<User> resultQuery = entityManager.createQuery(criteriaQuery);
        return resultQuery.getResultList().size() != 0 ? resultQuery.getSingleResult() : null;
    }

    public ResponseDTO<String> sendForgotPasswordEmail(String email, String requestUrl) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            User user = findByEmail(email);
            if (user == null) {
                responseDTO.setFailureResponse(null, StringUtil.NO_EMAIL);
            } else {
                String token = AppUtil.generateRandomUUID();
                user.setUuid(token);
                userRepository.save(user);
                requestUrl = requestUrl + "?token=" + token;
                responseDTO.setSuccessResponse(requestUrl, StringUtil.FORGOT_PASSWORD);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setFailureResponse(null, StringUtil.INTERNAL_SERVER_ERROR);
        }
        return responseDTO;
    }

    public ResponseDTO<String> verifyForgotPasswordToken(String email, String token) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            User user = findByEmailAndUuid(email, token);
            if (user == null) {
                responseDTO.setFailureResponse(null, StringUtil.LINK_EXPIRED);
            } else {
                user.setUuid(null);
                userRepository.save(user);
                responseDTO.setSuccessResponse(null, StringUtil.USER_VERIFIED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setFailureResponse(null, StringUtil.INTERNAL_SERVER_ERROR);
        }
        return responseDTO;
    }

    public User findByEmailAndUuid(String email, String uuid) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Predicate emailPredicate = builder.equal(root.get("email"), email);
        Predicate tokenPredicate = builder.equal(root.get("uuid"), uuid);
        query.select(root).where(emailPredicate, tokenPredicate);
        TypedQuery<User> resultQuery = entityManager.createQuery(query);
        return resultQuery.getResultList().size() != 0 ? resultQuery.getSingleResult() : null;
    }
}
