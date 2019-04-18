package com.example.AutoservicePortal.Repository;

import com.example.AutoservicePortal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
