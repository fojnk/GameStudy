package ru.nsu.GameStudy.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task_results")
public class TaskResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="task_id", referencedColumnName = "id")
    private Task task;
    @ManyToOne
    @JoinColumn(name="student_id", referencedColumnName = "id")
    private Student student;
    @Column(name="experience")
    private Integer experience;
    @Column(name="time")
    private Time time;
}
