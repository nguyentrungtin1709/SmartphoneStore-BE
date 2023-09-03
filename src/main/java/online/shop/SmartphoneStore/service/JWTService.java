package online.shop.SmartphoneStore.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import online.shop.SmartphoneStore.entity.Account;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JWTService {
    private final String SECRETKEY = "HLxxGg0GpsydbQBwG9Y3DPF4S7Uc3f2awJ9CcSnSPeo=";

    private final SignatureAlgorithm SIGNATUREALGORITHM = SignatureAlgorithm.HS256;

    private final Long EXPIRATION = 1000 * 60 * 60 * 24 * 7L;

    private SecretKey getSecretKey(){
        return new SecretKeySpec(
                Decoders.BASE64URL.decode(SECRETKEY),
                SIGNATUREALGORITHM.getJcaName()
        );
    }

    public String generateToken(Map<String, Object> extraClaims, Account account){
        Map<String, Object> privateClaims = new HashMap<>();
        privateClaims.put("id", account.getId().toString());
        privateClaims.put("email", account.getEmail());
        privateClaims.put("name", account.getName());
        return Jwts
                .builder()
                .signWith(
                        getSecretKey()
                )
                .addClaims(extraClaims)
                .setSubject(
                        account.getEmail()
                )
                .setIssuedAt(
                        new Date(System.currentTimeMillis())
                )
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION)
                )
                .addClaims(privateClaims)
                .compact();
    }

    public String generateToken(Account account){
        return generateToken(
                new HashMap<>(),
                account
        );
    }

    private Claims extractClaims(String token) throws JwtException{
        return Jwts
                .parserBuilder()
                .setSigningKey(
                    getSecretKey()
                )
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> function) throws JwtException {
        return function.apply(
                extractClaims(token)
        );
    }

    private Optional<String> extractEmail(String token){
        try {
            return Optional.ofNullable(
                    extractClaim(token, Claims::getSubject)
            );
        } catch (JwtException exception){
            return Optional.empty();
        }
    }

    private Optional<Date> extractExpiration(String token){
        try {
            return Optional.ofNullable(
                    extractClaim(token, Claims::getExpiration)
            );
        } catch (JwtException exception){
            return Optional.empty();
        }
    }

    private boolean isValidToken(String token, Account account){
        if (extractEmail(token).isEmpty() && extractExpiration(token).isEmpty()){
            return false;
        }
        return extractEmail(token).orElseThrow()
                .equals(
                        account.getEmail()
                ) &&
                extractExpiration(token).orElseThrow()
                    .after(
                            new Date(System.currentTimeMillis())
                    );
    }

}
