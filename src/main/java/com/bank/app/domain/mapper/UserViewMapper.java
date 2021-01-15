package com.bank.app.domain.mapper;

import com.bank.app.domain.dto.UserView;
import com.bank.app.domain.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 12/01/2021
 */
@Mapper(componentModel = "spring")
public abstract class UserViewMapper {

    @Mapping(target = "roles", ignore = true)
    public abstract UserView toUserView(User user);

    public abstract List<UserView> toUserView(List<User> users);

    @AfterMapping
    protected void afterUserView(User User, @MappingTarget UserView userView) {
        if (User.getRoles() != null) {
            userView.setRoles(User.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
        }
    }

}
