package pl.nieckarz.cryptocurrencyapi.services.utils;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import pl.nieckarz.cryptocurrencyapi.Secrets;

@Component
public class TokenUtil {

    public static Long getUserIdFromToken(HttpServletRequest request) {
        // Get the JWT token from the Authorization header
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        String jwtToken = authorizationHeader.substring(7);

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Secrets.apiKey.getBytes())
                    .parseClaimsJws(jwtToken)
                    .getBody();

            // Check if the token has expired
            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())) {
                return null;
            }

            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            return null;
        }
    }
}