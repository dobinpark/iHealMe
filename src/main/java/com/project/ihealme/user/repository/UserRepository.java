package com.project.ihealme.user.repository;

import com.project.ihealme.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByName(String name);

//    Optional<User> findByEmailAndPassword(String email, String password);
}