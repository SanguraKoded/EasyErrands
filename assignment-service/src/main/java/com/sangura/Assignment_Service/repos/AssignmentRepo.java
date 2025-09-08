package com.sangura.Assignment_Service.repos;

import com.sangura.Assignment_Service.entities.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepo extends JpaRepository<Assignment, Long> {
}
