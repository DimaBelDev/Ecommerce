package com.artland.start.ecommercebackend.service.impl;

import com.artland.start.ecommercebackend.api.dao.request.LoginRequest;
import com.artland.start.ecommercebackend.api.dao.request.RegistrationRequest;
import com.artland.start.ecommercebackend.api.dao.response.JwtAuthenticationResponse;
import com.artland.start.ecommercebackend.exception.EmailFailureException;
import com.artland.start.ecommercebackend.exception.UserAlreadyExistException;
import com.artland.start.ecommercebackend.exception.UserNotVerifiedException;
import com.artland.start.ecommercebackend.model.LocalUser;
import com.artland.start.ecommercebackend.model.Role;
import com.artland.start.ecommercebackend.model.VerificationToken;
import com.artland.start.ecommercebackend.repository.LocalUserRepository;
import com.artland.start.ecommercebackend.repository.VerificationTokenRepository;
import com.artland.start.ecommercebackend.service.AuthenticationService;
import com.artland.start.ecommercebackend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final LocalUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final VerificationTokenRepository verificationTokenRepository;

    // correct email
    @Override
    public LocalUser register(RegistrationRequest request) throws UserAlreadyExistException, EmailFailureException {
        if(userRepository.findByUsername(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistException();
        }
        var user = LocalUser.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).username(request.getEmail())
                .role(Role.USER).build();

        var verificationToken = createVerificationToken(user);
        emailService.sendVerificationEmail(verificationToken);
        return userRepository.save(user);
    }


    // check authenticationManager.authenticate
    @Override
    public JwtAuthenticationResponse login(LoginRequest request) throws EmailFailureException, UserNotVerifiedException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        if(user.isEnabled()) {
            var jwt = jwtService.generateToken(user);
            return JwtAuthenticationResponse.builder().token(jwt).build();
        } else {
            List<VerificationToken> verificationTokens = user.getVerificationTokens();
            boolean resend = verificationTokens.isEmpty() ||
                    verificationTokens.get(0).getCrearedTimestamp().before(new Timestamp(System.currentTimeMillis() - (24*60*60*1000)));
            if(resend) {
                VerificationToken verificationToken = createVerificationToken(user);
                verificationTokenRepository.save(verificationToken);
                emailService.sendVerificationEmail(verificationToken);
            }
            throw new UserNotVerifiedException(resend);
        }
    }

    private VerificationToken createVerificationToken(LocalUser user) {
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setToken(jwtService.generateVerificationToken(user));
    verificationToken.setCrearedTimestamp(new Timestamp(System.currentTimeMillis()));
    verificationToken.setUser(user);
    user.getVerificationTokens().add(verificationToken);
    return verificationToken;
    }

    public boolean verifyUser(String token) {
        Optional<VerificationToken> opToken = verificationTokenRepository.findByToken(token);
        if (opToken.isPresent()) {
            VerificationToken verificationToken = opToken.get();
            LocalUser user = verificationToken.getUser();
            if (!user.isEnabled()) {
                user.setEmailVerified(true);
                userRepository.save(user);
                verificationTokenRepository.deleteByUser(user);
                return true;
            }
        }
        return false;
    }
}
