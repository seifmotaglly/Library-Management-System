package com.library.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.library.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtUtil jwtUtil;

    public String generateToken(UserDetails userDetails){
        return jwtUtil.generateToken(userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return jwtUtil.generateToken(extraClaims, userDetails);
    }

    

    public String extractUsername(String jwtToken) {
        return jwtUtil.extractUsername(jwtToken);
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        return jwtUtil.isTokenValid(jwtToken, userDetails);
    }

    

}
