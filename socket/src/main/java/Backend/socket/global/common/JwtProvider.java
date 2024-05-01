package Backend.socket.global.common;

import Backend.socket.global.error.socketException.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;

import static Backend.socket.global.error.ErrorCode.EXPIRED_ACCESS_TOKEN;
import static Backend.socket.global.error.ErrorCode.INVALID_ACCESS_TOKEN_VALUE;


@Getter
@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.access-token-expire-time}")
    private long ACCESS_TOKEN_EXPIRE_TIME;
    @Value("${jwt.refresh-token-expire-time}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    public void validateAccessToken(String accessToken) {
        try {
            getJwtParser().parseClaimsJws(accessToken);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(EXPIRED_ACCESS_TOKEN);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnauthorizedException(INVALID_ACCESS_TOKEN_VALUE);
        }
    }

    public Long getSubject(String token) {
        return Long.valueOf(getJwtParser().parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

    private JwtParser getJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build();
    }

    private Key getSigningKey() {
        String encoded = Base64.getEncoder().encodeToString(secretKey.getBytes());
        return Keys.hmacShaKeyFor(encoded.getBytes());
    }
}

