package com.sangura.Auth_Service.Repos;

import com.sangura.Auth_Service.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUsersRepo extends JpaRepository<AppUser, Long> {

    public Optional<AppUser> findByUsername(String username);
}
