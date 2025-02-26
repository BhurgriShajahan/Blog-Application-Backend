package blog.app.config;

import blog.app.exceptions.CustomJwtException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class JwtUtil {

    private final JwtConfig jwtConfig;

    @Autowired
    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String getSecretKey() {
        return jwtConfig.getSecretKey();
    }

    public String generateToken(String username, Set<GrantedAuthority> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        SecretKey secretKey = Keys.hmacShaKeyFor(getSecretKey().getBytes()); // Ensure the secret key is a byte array

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 day
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(getSecretKey().getBytes()); // Extract secret key from config

            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new CustomJwtException("Invalid JWT token: " + e.getMessage(), e);
        }
    }

    public boolean validateToken(String token, String username) {
        try {
            Claims claims = getClaims(token);
            String extractedUsername = claims.getSubject();
            return extractedUsername.equals(username) && !claims.getExpiration().before(new Date());
        } catch (JwtException e) {
            throw new CustomJwtException("Token validation failed.", e);
        }
    }

}
