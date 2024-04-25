package com.artland.start.ecommercebackend.repository;

import com.artland.start.ecommercebackend.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalUserRepository extends JpaRepository<LocalUser, Long> {
    Optional<LocalUser> findByUsername(String username);

    Optional<LocalUser> findByEmail(String email);





}
