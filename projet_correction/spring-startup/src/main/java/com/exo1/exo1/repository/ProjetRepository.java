package com.exo1.exo1.repository;

import com.exo1.exo1.entity.Projet;
import com.exo1.exo1.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjetRepository extends JpaRepository<Projet, Long> {
    @Query("SELECT p FROM Task tache LEFT JOIN FETCH tache.user WHERE tache.id = :id")
    Optional<Task> findByIdWithUser(@Param("id") Long id);
}
