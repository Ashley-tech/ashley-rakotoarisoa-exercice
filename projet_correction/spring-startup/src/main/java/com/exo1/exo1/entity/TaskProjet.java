package com.exo1.exo1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "projet_task_number")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskProjet {
    @Id
    private String projet;

    @Column(name = "nombre_de_taches")
    private Long nombreTaches;
}
