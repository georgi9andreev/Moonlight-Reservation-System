package com.bootcamp3.MoonlightHotelAndSpa.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.SecurityConstant.AUTHORITIES;
import static com.bootcamp3.MoonlightHotelAndSpa.constant.SecurityConstant.JWT_TOKEN_VALIDITY;

@Component
public class JwtTokenUtil {


    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()
                .setClaims(getClaimsFromUser(userDetails))
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(JWT_TOKEN_VALIDITY, ChronoUnit.SECONDS)))
                .signWith(key)
                .compact();
    }

    private Claims getClaimsFromUser(UserDetails user) {

        Claims claims = Jwts.claims();
        claims.put(AUTHORITIES, user.getAuthorities());
        return claims;
    }

    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
