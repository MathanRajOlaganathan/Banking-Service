package com.bank.app.controller;

import com.bank.app.config.security.JwtTokenFilter;
import com.bank.app.config.security.JwtTokenUtil;
import com.bank.app.domain.dto.JwtTokenResponse;
import com.bank.app.domain.dto.LoginRequest;
import com.bank.app.domain.dto.UserView;
import com.bank.app.domain.mapper.UserViewMapper;
import com.bank.app.domain.model.User;
import com.bank.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import static com.bank.app.constants.ApplicationConstants.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 12/01/2021
 */
@RestController
@RequestMapping(path = AUTH_PATH)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Auth REST endpoints")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping(AUTH_LOGIN)
    @Operation(summary = "Authenticate User", description = "Authenticate User using username and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        try {
            log.debug("Logging In");
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            User user = (User) authenticate.getPrincipal();
            String token = jwtTokenUtil.generateAccessToken(user);
            log.debug("User Authenticated Successfully");
            return ResponseEntity.ok(new JwtTokenResponse(token));
        } catch (BadCredentialsException ex) {
            log.error("Bad Credential");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad Credential");
        }
    }

    @GetMapping(AUTH_LOGOUT)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Logging Out", description = "Logging Out")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.debug("Logging out");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
            jwtTokenUtil.addBlackListJwt(JwtTokenFilter.getJwtFromRequest(request).get());
            log.debug("User Logged Out Successfully");
            return ResponseEntity.ok("User Logged Out Successfully");
        } catch (Exception ex) {
            log.error("Error in adding the token to the blacklist");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
