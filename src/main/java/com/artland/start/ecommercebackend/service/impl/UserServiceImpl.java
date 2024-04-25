package com.artland.start.ecommercebackend.service.impl;


import com.artland.start.ecommercebackend.api.dao.request.RegistrationRequest;
import com.artland.start.ecommercebackend.exception.UserAlreadyExistException;
import com.artland.start.ecommercebackend.model.LocalUser;
import com.artland.start.ecommercebackend.repository.LocalUserRepository;
import com.artland.start.ecommercebackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private LocalUserRepository localUserRepository;

//    public LocalUser registerUser(RegistrationRequest registrationRequest) throws UserAlreadyExistException {
//
//        if(localUserRepository.findByUsernameIgnoreCase(registrationRequest.getUsername()).isPresent()
//           || localUserRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
//            throw new UserAlreadyExistException();
//        }
//
//        LocalUser user = new LocalUser();
//        user.setEmail(registrationRequest.getEmail());
//        user.setFirstName(registrationRequest.getFirstName());
//        user.setLastName(registrationRequest.getLastName());
//        user.setUsername(registrationRequest.getUsername());
//
//        user.setPassword(registrationRequest.getPassword());
//
//       return localUserRepository.save(user);
//
//    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return localUserRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User is not found"));
            }
        };
    }


}
