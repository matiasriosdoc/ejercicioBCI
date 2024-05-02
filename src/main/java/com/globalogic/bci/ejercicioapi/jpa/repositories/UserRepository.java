package com.globalogic.bci.ejercicioapi.jpa.repositories;

import com.globalogic.bci.ejercicioapi.jpa.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

}