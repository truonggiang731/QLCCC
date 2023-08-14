package com.springboot.blog.repository;

import com.springboot.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);


    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
//    @Query("SELECT r.id FROM User u JOIN u.roles r WHERE u.email = :email")
//    Long findRoleIdByEmail(@Param("email") String email);
}
