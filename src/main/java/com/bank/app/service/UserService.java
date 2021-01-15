package com.bank.app.service;

import com.bank.app.domain.dto.UpdateUserRequest;
import com.bank.app.domain.dto.UserRequest;
import com.bank.app.domain.dto.UserView;
import com.bank.app.domain.mapper.UserMapper;
import com.bank.app.domain.mapper.UserViewMapper;
import com.bank.app.domain.model.User;
import com.bank.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 12/01/2021
 */
@Service
@Slf4j
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserViewMapper userViewMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Retrieving UserDetails by username");
        return userRepository
                .findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(format("User with username - %s, not found", username))
                );
    }


    /**
     * Merge User
     *
     * @param userRequest
     * @return
     */
    @Transactional
    public UserView upsert(UserRequest userRequest) {
        log.debug("User upsert");
        Optional<User> optionalUser = userRepository.findByUsername(userRequest.getUsername());

        if (optionalUser.isEmpty()) {
            return create(userRequest);
        } else {
            UpdateUserRequest updateUserRequest = new UpdateUserRequest();
            updateUserRequest.setFullName(userRequest.getFullName());
            return update(optionalUser.get().getId(), updateUserRequest);
        }
    }

    /**
     * Update User
     *
     * @param id
     * @param request
     * @return
     */
    @Transactional
    public UserView update(Long id, UpdateUserRequest request) {
        User user = userRepository.getById(id);
        userMapper.update(request, user);
        user = userRepository.save(user);
        log.debug("User updated successfully");
        return userViewMapper.toUserView(user);
    }

    /**
     * Create User
     *
     * @param userRequest
     * @return
     */
    @Transactional
    public UserView create(UserRequest userRequest) {
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }
        if (!userRequest.getPassword().equals(userRequest.getRePassword())) {
            throw new ValidationException("Passwords don't match!");
        }
        if (userRequest.getRoles() == null) {
            userRequest.setRoles(new HashSet<>());
        }

        User user = userMapper.create(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        user = userRepository.save(user);
        log.debug("User created successfully");
        return userViewMapper.toUserView(user);
    }

    /**
     * Delete User
     *
     * @param id
     * @return
     */
    @Transactional
    public UserView delete(Long id) {
        User user = userRepository.getById(id);
        user.setUsername(user.getUsername().replace("@", String.format("_%s@", user.getId().toString())));
        user.setEnabled(false);
        user = userRepository.save(user);
        log.debug("User disabled successfully");
        return userViewMapper.toUserView(user);
    }

    /**
     * Find User
     *
     * @param id
     * @return
     */
    public UserView getUser(Long id) {
        User user = userRepository.getById(id);
        log.debug("User retrieved successfully");
        return userViewMapper.toUserView(user);
    }

    /**
     * Find all users
     *
     * @return
     */
    public List<UserView> getAllUsers() {
        List<User> user = userRepository.findAll();
        log.debug("All Users retrieved successfully");
        return userViewMapper.toUserView(user);
    }


}
