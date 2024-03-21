package com.example.Chat.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private  static final String SECRET_KEY ="5367566B59703373357638792F423F4528482B4D6251655468576D5A71347437";
    //Extract username from jeton JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    //Extract a Specific Claim
    public <T>  T extractClaim(String token , Function<Claims, T> claimsResolver){
        final Claims claims= extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    //Generate Token
    public String generateToken(
            Map<String , Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername()) //Should be Unique for Spring is always called Username
                .setIssuedAt(new Date(System.currentTimeMillis())) //To check if the validation of Token is expired or no
                .setExpiration(new Date(System.currentTimeMillis() + 1000 *60 *24 ))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();//Generate and return the Token
    }

    // Method help us to Verify that token Valid or not
    public boolean isTokenValid(String token , UserDetails userDetails){
        final String username= extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
   // check if Token Expired or not
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims:: getExpiration);
    }

    //Extract one single claim (Jeton JWT)
    //Use bib Java jwt (JJWT) to verify Signature
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder() //ParserBuilder to create a parser JWT
                .setSigningKey(getSignInKey()) // Specify Signature Key JWT used in integration of jeton --> Get key
                .build()
                .parseClaimsJws(token) // Analyze Jeton and Verify Signature
                .getBody(); //Extract claims Jeton and return it
    }
    //Get SignInKey Methode using in previous Function
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }
}
