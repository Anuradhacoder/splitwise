package com.example.splitwise.Repository;

import com.example.splitwise.Model.GroupTable;
import com.example.splitwise.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmailAndDeleted(String email, boolean deleted);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(String userId);
    Optional<User> findByEmailAndPassword(String email,String password);
}
