package com.artland.start.ecommercebackend.repository;

import com.artland.start.ecommercebackend.model.LocalUser;
import com.artland.start.ecommercebackend.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    void deleteByUser(LocalUser localUser);

}
