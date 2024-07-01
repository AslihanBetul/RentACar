package com.abm.repository;

import com.abm.dto.request.LoginRequestDto;
import com.abm.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    boolean existsByUsername(String username);

   Optional<Auth> findOptionalByUsernameAndPassword(String username, String password);

    Optional<Auth> findOptionalByEmailAndPassword(String email,String password);

   Optional<Auth>  findOptionalByEmail(String email);
}
