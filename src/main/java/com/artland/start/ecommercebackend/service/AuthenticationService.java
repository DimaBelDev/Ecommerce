package com.artland.start.ecommercebackend.service;


import com.artland.start.ecommercebackend.api.dao.request.LoginRequest;
import com.artland.start.ecommercebackend.api.dao.request.RegistrationRequest;
import com.artland.start.ecommercebackend.api.dao.response.JwtAuthenticationResponse;
import com.artland.start.ecommercebackend.exception.EmailFailureException;
import com.artland.start.ecommercebackend.exception.UserAlreadyExistException;
import com.artland.start.ecommercebackend.exception.UserNotVerifiedException;
import com.artland.start.ecommercebackend.model.LocalUser;

public interface AuthenticationService {
    LocalUser register(RegistrationRequest request) throws UserAlreadyExistException, EmailFailureException;

    JwtAuthenticationResponse login(LoginRequest request) throws EmailFailureException, UserNotVerifiedException;

    boolean verifyUser(String token);

}
