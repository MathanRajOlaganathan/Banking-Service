package com.bank.app.controller;


import com.bank.app.domain.dto.CustomerRequest;
import com.bank.app.domain.dto.CustomerView;
import com.bank.app.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bank.app.constants.ApplicationConstants.CUSTOMER_PATH;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
@RestController
@RequestMapping(CUSTOMER_PATH)
@Tag(name = "Customer REST endpoints")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('USER')")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    @Operation(summary = "Get customer details", description = "Get Customer details by customer id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = CustomerView.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public CustomerView getCustomer(@PathVariable Long id) {
        log.debug("Find Customer");
        return customerService.find(id);
    }

    @GetMapping()
    @Operation(summary = "Find all customers", description = "Gets details of all the customers")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = CustomerView.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public List<CustomerView> getAllCustomers() {
        log.debug("Find all Customers");
        return customerService.findAll();
    }

    @PostMapping()
    @Operation(summary = "Add a Customer", description = "Add customer")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public CustomerView addCustomer(@RequestBody CustomerRequest customer) {
        log.debug("Adding Customer");
        return customerService.addCustomer(customer);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer", description = "Update customer details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = CustomerView.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public CustomerView updateCustomer(@RequestBody CustomerRequest customerRequest,
                                       @PathVariable Long id) {
        log.debug("Updating KYC for the Customer");
        return customerService.updateCustomer(customerRequest, id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer and related accounts", description = "Delete customer")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
        log.debug("Deleting Customer");
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted Successfully");
    }
}
