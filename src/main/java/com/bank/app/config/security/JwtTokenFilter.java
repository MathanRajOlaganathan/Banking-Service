package com.bank.app.config.security;

import com.bank.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Optional;

import static com.bank.app.constants.ApplicationConstants.BEARER;
import static java.util.List.of;
import static java.util.Optional.ofNullable;

/**
 * A filter to intercept each request, extract jwt and validate
 *
 * @author Mathan Raj O
 * @version 1.0
 * @since 12/01/2021
 */
@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        log.debug("Authentication Request For '{}'", request.getRequestURL());

        final Optional<String> jwt = getJwtFromRequest(request);

        jwt.ifPresent(token -> {
            //check if the token is in the blacklist
            if (jwtTokenUtil.isTokenBlacklisted(token)) {
                log.info("blacklisted token");
                try {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                } catch (IOException ex) {
                    log.error("Invalid JWT token - {}", ex.getMessage());
                }
            }
                else if (jwtTokenUtil.validateToken(token)) {
                    UserDetails userDetails = userService.loadUserByUsername(jwtTokenUtil.getUsername(token));
                    setSecurityContext(new WebAuthenticationDetailsSource().buildDetails(request), userDetails);
                }
        });
        chain.doFilter(request, response);
    }

    /**
     * Setting the Authentication in the spring security context
     *
     * @param authDetails
     * @param userDetails
     */
    private void setSecurityContext(WebAuthenticationDetails authDetails, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(of())
        );
        authentication.setDetails(authDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Method to extract jwt token from the request header
     *
     * @param request
     * @return
     */
    public static Optional<String> getJwtFromRequest(HttpServletRequest request) {
        log.debug("extract JWT from the request");
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }
}
