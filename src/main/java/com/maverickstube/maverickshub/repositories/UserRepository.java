package com.maverickstube.maverickshub.repositories;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.maverickstube.maverickshub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select s from User u where u.email =: email")
    Optional<User> findByEmail(String email);
}

