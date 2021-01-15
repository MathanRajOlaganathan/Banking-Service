package com.bank.app.config.security;

import com.bank.app.domain.model.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.bank.app.constants.ApplicationConstants.ISSUER;

/**
 * An utility to create and validate jwt token.
 * @author Mathan Raj O
 * @version 1.0
 * @since 12/01/2021
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenUtil {
    private HashMap<String,Date> jwtBlackListMap = new HashMap();

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expirationMs}")
    private long EXPIRATION_TIME;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }


    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user);
    }

    /**
     * Add the non expired token to the blacklist on logout
     * @param token
     */
    public void addBlackListJwt(String token){
        if(!isTokenExpired(token)) {
            jwtBlackListMap.put(token, extractExpiration(token));
            log.info("Token blacklisted");
        }
    }

    public  boolean isTokenBlacklisted(String token){
        return jwtBlackListMap.containsKey(token);
    }

    /**
     * At 00:00:00am every day the blacklisted token checked for expiration and will
     * be removed
     */
    @Scheduled(cron = "0 0 0 * * ?")
    protected void deleteExpiredTokens() {
        log.debug("Delete expired tokens from blacklist, currentSize: ",jwtBlackListMap.size());
        jwtBlackListMap.entrySet().removeIf(e->e.getValue().before(new Date(System.currentTimeMillis())));
        log.debug("Removed {} entry from the jwtBlackListMap",jwtBlackListMap.size());
    }

    private String createToken(Map<String, Object> claims, User user) {
        return Jwts.builder()
                .setClaims(claims)
                .setId(String.valueOf(user.getId()))
                .setSubject(user.getUsername())
                .setIssuer(ISSUER)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    public String getUserId(String token) {
        return extractClaim(token, Claims::getId);
    }

    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token) {
        var isValid=false;
        try {
            final String username = extractUsername(token);
            isValid =(username != null && !isTokenExpired(token));
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return isValid;
    }
}
