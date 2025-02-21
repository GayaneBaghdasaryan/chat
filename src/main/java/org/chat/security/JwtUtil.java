package org.chat.security;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.chat.exception.BaseException;

import java.util.Date;

import static org.chat.common.ErrorCode.TOKEN_EXPIRED;
import static org.chat.common.ErrorCode.TOKEN_INVALID;

public class JwtUtil {
    public static JWTClaimsSet parseAndValidateToken(String token) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

        Date expirationTime = claims.getExpirationTime();
        if (expirationTime == null || new Date().after(expirationTime)) {
            throw new BaseException(TOKEN_EXPIRED, expirationTime.toString());
        }

        if (!"chat-quarkus".equals(claims.getIssuer())) {
            throw new BaseException(TOKEN_INVALID, claims.getIssuer());
        }

        return claims;
    }
}
