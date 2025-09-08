package com.sangura.Auth_Service.Repos;

import com.sangura.Auth_Service.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUsersRepo extends JpaRepository<AppUser, Long> {
}
