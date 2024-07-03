package com.ejemplo.security.repository;

import com.ejemplo.security.model.OurUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OurUserRepository extends JpaRepository<OurUser,Integer> {
    @Query(value = "select * from usuario where email =?1", nativeQuery = true)
    Optional<OurUser> findByEmail(String email);
}
