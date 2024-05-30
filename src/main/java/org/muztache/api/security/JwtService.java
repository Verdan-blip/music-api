package org.muztache.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${token.accessSecret}")
    private String accessSecret;

    @Value("${token.refreshSecret}")
    private String refreshSecret;

    @Getter
    @Value("${token.accessExpirationMs}")
    private long accessExpirationMs;

    @Getter
    @Value("${token.refreshExpirationMs}")
    private long refreshExpirationMs;

    private <T> T extractClaim(String token, String secret, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token, secret);
        return claimsResolvers.apply(claims);
    }

    private Date extractExpiration(String token, String secret) {
        return extractClaim(token, secret, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token, String secret) {
        return Jwts.parser()
                .verifyWith(getSecretKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSecretKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private boolean isTokenNotExpired(String token, String secret) {
        return !extractExpiration(token, secret).before(new Date());
    }

    public String generateAccessToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + accessExpirationMs))
                .signWith(getSecretKey(accessSecret), Jwts.SIG.HS256)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + refreshExpirationMs))
                .signWith(getSecretKey(refreshSecret), Jwts.SIG.HS256)
                .compact();
    }

    public Claims extractAllAccessClaims(String token) {
        return extractAllClaims(token, accessSecret);
    }

    public Claims extractAllRefreshClaims(String token) {
        return extractAllClaims(token, refreshSecret);
    }

    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) &&
                isTokenNotExpired(token, accessSecret);
    }

    public boolean isRefreshTokenValid(String token) {
        return isTokenNotExpired(token, refreshSecret);
    }

    public String extractUserName(String token) {
        return extractClaim(token, accessSecret, Claims::getSubject);
    }
}
