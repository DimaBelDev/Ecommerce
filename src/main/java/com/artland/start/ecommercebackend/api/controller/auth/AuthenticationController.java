package com.artland.start.ecommercebackend.api.controller.auth;

import com.artland.start.ecommercebackend.api.dao.request.LoginRequest;
import com.artland.start.ecommercebackend.api.dao.request.RegistrationRequest;
import com.artland.start.ecommercebackend.api.dao.response.JwtAuthenticationResponse;
import com.artland.start.ecommercebackend.exception.EmailFailureException;
import com.artland.start.ecommercebackend.exception.UserAlreadyExistException;
import com.artland.start.ecommercebackend.service.AuthenticationService;
import com.artland.start.ecommercebackend.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegistrationRequest registrationRequest
    ) {
        try {
            authenticationService.register(registrationRequest);
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> loginUser(
            @RequestBody LoginRequest loginRequest
            ) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        if (authenticationService.verifyUser(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }


}
