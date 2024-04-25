package com.globalogic.bci.ejercicioapi.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.globalogic.bci.ejercicioapi.jpa.domains.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}