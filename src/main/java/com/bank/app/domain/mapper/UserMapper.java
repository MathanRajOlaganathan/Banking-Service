package com.bank.app.domain.mapper;

import com.bank.app.domain.dto.UpdateUserRequest;
import com.bank.app.domain.dto.UserRequest;
import com.bank.app.domain.model.Role;
import com.bank.app.domain.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 12/01/2021
 */
@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Mapping(target = "roles", ignore = true)
    public abstract User create(UserRequest userRequest);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "roles", ignore = true)
    public abstract void update(UpdateUserRequest request, @MappingTarget User user);


    @AfterMapping
    protected void afterCreate(UserRequest userRequest, @MappingTarget User user) {
        if (userRequest.getRoles() != null) {
            user.setRoles(userRequest.getRoles().stream().map(Role::new).collect(Collectors.toSet()));
        }
    }

    @AfterMapping
    protected void afterUpdate(UpdateUserRequest request, @MappingTarget User user) {
        if (request.getRoles() != null) {
            user.setRoles(request.getRoles().stream().map(Role::new).collect(Collectors.toSet()));
        }
    }
}
