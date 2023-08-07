package nisum.latam.etecnico.jwtconfig;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class JwtConfig {

    private final String secret = "1234";
    private final long expiration = 86400000; // 24 horas en milisegundos

    @Bean
    public JwtParser jwtParser() {
        return Jwts.parser().setSigningKey(secret);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}