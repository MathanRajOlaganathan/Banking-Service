//package com.bank.app.repository;
//
//import com.bank.app.domain.dto.UserRequest;
//import com.bank.app.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Set;
//
///**
// * @author Mathan Raj O
// * @version 1.0
// * @since 12/01/2021
// */
//@Component
//public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {
//
//    @Autowired
//    private UserService userService;
//
//    private final List<String> usernames = List.of(
//            "admin@email.com",
//            "employee@email.com",
//            "user@email.com"
//    );
//
//    private final List<String> fullNames = List.of(
//            "Admin User",
//            "Employee User",
//            "Test User"
//    );
//    private final List<String> roles = List.of(
//            "ROLE_ADMIN", "ROLE_USER", "ROLE_USER"
//    );
//
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
//        for (int i = 0; i < usernames.size(); ++i) {
//            String password = usernames.get(i).replaceAll("((@.*)|[^a-zA-Z])+", " ").trim();
//            UserRequest request = new UserRequest();
//            request.setUsername(usernames.get(i));
//            request.setFullName(fullNames.get(i));
//            request.setPassword(password);
//            request.setRePassword(password);
//            request.setRoles(Set.of(roles.get(i)));
//
//            userService.upsert(request);
//        }
//    }
//}
