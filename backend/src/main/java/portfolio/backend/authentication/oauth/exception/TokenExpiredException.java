package portfolio.backend.authentication.oauth.exception;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

public class TokenExpiredException extends RuntimeException {

    private Claims claims;

    public TokenExpiredException(String message, ExpiredJwtException e) {
        super(message, e);
        this.claims = e.getClaims();
    }

    public Claims getClaims() {
        return claims;
    }
}