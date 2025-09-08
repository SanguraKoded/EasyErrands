package com.sangura.Errand_Service.repos;

import com.sangura.Errand_Service.entities.Tasker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskerRepo extends JpaRepository<Tasker, Long> {
}
