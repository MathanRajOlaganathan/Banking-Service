package com.bank.app.controller;

import com.bank.app.domain.dto.UserRequest;
import com.bank.app.domain.dto.UserView;
import com.bank.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.bank.app.constants.ApplicationConstants.ADMIN_PATH;
import static com.bank.app.constants.ApplicationConstants.EMPLOYEE;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 12/01/2021
 */
@RestController
@RequestMapping(path = ADMIN_PATH)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin REST endpoints")
@Slf4j
public class AdminController {

    private final UserService userService;


    @PostMapping(EMPLOYEE)
    @Operation(summary = "Create User", description = "Create New User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = UserView.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public UserView addEmployee(@RequestBody @Valid UserRequest userRequest) {
        log.debug("Add Employee");
        return userService.create(userRequest);

    }

    @DeleteMapping(EMPLOYEE + "/{id}")
    @Operation(summary = "Delete Employee", description = "Delete Employee using the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = UserView.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public UserView deleteEmployee(@PathVariable("id") Long id) {
        log.debug("Delete Employee");
        return userService.delete(id);

    }

    @GetMapping(EMPLOYEE + "/{id}")
    @Operation(summary = "Find an Employee", description = "Find an Employee with the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = UserView.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public UserView getEmployee(@PathVariable("id") Long id) {
        log.debug("Find Employee");
        return userService.getUser(id);
    }

    @GetMapping(EMPLOYEE)
    @Operation(summary = "Find All Employees", description = "Find All Employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = UserView.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public List<UserView> employees() {
        log.debug("Find All Employees");
        return userService.getAllUsers();
    }
}
