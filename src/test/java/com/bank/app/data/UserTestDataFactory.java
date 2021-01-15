package com.bank.app.data;

import com.bank.app.domain.dto.UserRequest;
import com.bank.app.domain.dto.UserView;
import com.bank.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 14/01/2021
 */
@Service
public class UserTestDataFactory {


    @Autowired
    private UserService userService;

    public UserView createUser(String username,
                               String fullName,
                               String password,Set<String> roles) {
        UserRequest createRequest = new UserRequest();
        createRequest.setUsername(username);
        createRequest.setFullName(fullName);
        createRequest.setPassword(password);
        createRequest.setRePassword(password);
        createRequest.setRoles(roles);

        UserView userView = userService.create(createRequest);

        assertNotNull(userView.getId(), "User id must not be null!");
        assertEquals(fullName, userView.getFullName(), "User name update isn't applied!");

        return userView;
    }

    public UserView createUser(String username,
                               String fullName) {
        return createUser(username, fullName, "Test12345_",Set.of("ROLE_USER"));
    }

    public void deleteUser(Long id) {
        userService.delete(id);
    }
}
