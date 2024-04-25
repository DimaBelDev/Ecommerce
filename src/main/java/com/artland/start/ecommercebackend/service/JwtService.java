package com.artland.start.ecommercebackend.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

        String extractUserName(String token);
        String generateToken(UserDetails userDetails);
        String generateVerificationToken(UserDetails userDetails);
        boolean isTokenValid(String token, UserDetails userDetails);
}
