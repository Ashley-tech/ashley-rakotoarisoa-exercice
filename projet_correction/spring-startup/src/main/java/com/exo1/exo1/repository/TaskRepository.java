package com.exo1.exo1.repository;

import com.exo1.exo1.entity.Task;
import com.exo1.exo1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT tache FROM Task tache LEFT JOIN FETCH tache.user WHERE tache.id = :id")
    Optional<Task> findByIdWithUser(@Param("id") Long id);
}
