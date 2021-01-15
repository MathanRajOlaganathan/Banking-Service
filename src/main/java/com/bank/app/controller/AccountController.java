package com.bank.app.controller;

import com.bank.app.domain.dto.*;
import com.bank.app.service.AccountService;
import com.bank.app.util.TransactionPDFGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

import static com.bank.app.constants.ApplicationConstants.*;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */

@RestController
@RequestMapping(ACCOUNT_PATH)
@Tag(name = "Accounts and Transactions REST endpoints")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('USER')")
public class AccountController {

    private final AccountService accountService;

    @PostMapping(LINK_ACCOUNT)
    @Operation(summary = "Link Customer with account", description = "Link Customer with the requested account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = CustomerAccountView.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public CustomerAccountView linkCustomerWithAccount(@RequestBody @Valid CustomerAccountRequest customerAccountRequest) {
        log.info("Linking Customer with the account");
        return accountService.linkCustomerWithAccount(customerAccountRequest);
    }

    @PostMapping()
    @Operation(summary = "Create Account", description = "Create Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = AccountView.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public AccountView createAccount(@RequestBody @Valid AccountRequest accountRequest) {
        log.debug("Creating Account Type");
        return accountService.createAccount(accountRequest);
    }

    @GetMapping(ACCOUNT_BALANCE+"/{accountNo}")
    @Operation(summary = "Get Account Balance", description = "Get Account Balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public Double getAccountBalance(@PathVariable("accountNo") Long accountNo) {
        log.debug("Getting Account Balance");
        return accountService.getAccountBalance(accountNo);
    }

    @PostMapping(TRANSFER_MONEY)
    @Operation(summary = "Transfer Money", description = "Transfer Money from one account to an another account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public String transferDetails(@RequestBody TransferRequest transferRequest) {
        log.debug("Transferring Money");
        return accountService.transferMoney(transferRequest);
    }

    @GetMapping(value = ACCOUNT_STATEMENT, produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Get account  statement", description = "Get account statement for the given date range.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = CustomerView.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public ResponseEntity<InputStreamResource> exportAccountStatementPDF(@RequestParam("accountNumber") Long accountNumber, @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate startDate, @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.debug("Get Account Statement");
        List<TransactionView> transactions = accountService.findTransactionsByAccountNumber(accountNumber, startDate, endDate);
        ByteArrayInputStream bis = TransactionPDFGenerator.statementReport(transactions);
        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=" + accountNumber + "_" + LocalDate.now() + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
