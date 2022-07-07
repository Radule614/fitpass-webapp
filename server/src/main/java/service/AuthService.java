package service;

import dto.auth.LoginDTO;
import dto.auth.LoginResponseDTO;
import dto.auth.RegisterDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.User;
import model.customer.Customer;

import javax.crypto.spec.SecretKeySpec;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class AuthService {
    private static final String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
    private static final long expirationLength = 60L * 72L;

    public AuthService() {}

    public LoginResponseDTO login(LoginDTO loginDTO) {
        User user = new UserService().getUser(loginDTO.username);
        if(user != null && user.password.equals(loginDTO.password)) {
            return createToken(user);
        }
        return null;
    }
    
    public Customer register(RegisterDTO registerDTO) {
    	return new UserService().createNewCustomer(registerDTO);
    }

    public String isTokenValid(String token){
        Jws<Claims> jws = null;
        try{
            jws = parseToken(token);
        }catch (Exception e){
            return null;
        }

        String username = jws.getBody().get("username").toString();
        return new UserService().getUser(username) == null ? null : username;
    }

    private LoginResponseDTO createToken(User user) {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());

        Instant now = Instant.now();
        Date time = Date.from(now.plus(expirationLength, ChronoUnit.MINUTES));
        String jwtToken = Jwts.builder()
                .claim("username", user.username)
                .setSubject(user.firstname)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(time)
                .signWith(hmacKey)
                .compact();

        return new LoginResponseDTO(jwtToken, time.getTime());
    }

    private Jws<Claims> parseToken(String jwtToken){
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(jwtToken);
    }
    
}
