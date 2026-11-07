package com.MarketConnect.GaadiDealer.services;

import com.MarketConnect.GaadiDealer.entities.User;
import com.MarketConnect.GaadiDealer.entities.UserType;
import com.MarketConnect.GaadiDealer.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretekey;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Autowired
    private UserRepository userRepository;
    public String generateToken(String email, UserType userType)
    {
        User user= userRepository.findByEmailId(email).orElseThrow(()->new NoSuchElementException("No such user in db with this Email exists!"));
        Map<String,Object> claims = new HashMap<>();
        return Jwts.builder()
                .claim("role",user.getUserType())
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .and()
                .signWith(getKey())
                .compact();
    }


    public String getClaim(String token)
    {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role",String.class);

    }

    public SecretKey getKey()
    {
        byte[] keybits= Decoders.BASE64.decode(secretekey);
        return Keys.hmacShaKeyFor(keybits);
    }


    public String extractUserName(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimResolver)
    {
        final Claims claims=extractAllClaims(token);
        return claimResolver.apply(claims);

    }

    private Claims extractAllClaims(String token)
    {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token)
    {
        final String userName=extractUserName(token);
        return ( !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token)
    {
        return extractClaim(token,Claims::getExpiration);
    }
}
