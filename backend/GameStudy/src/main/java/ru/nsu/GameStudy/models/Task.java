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
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="title")
    private String title;
    @Column(name="description")
    private String description;
    @ManyToOne
    @JoinColumn(name="discipline_id", referencedColumnName = "id")
    private Discipline discipline;
    @Column(name="time")
    private Time time;
    @Column(name="experience")
    private Integer experience;
    @Column(name="complexity")
    private Integer complexity;
    @Column(name="answer")
    private String answer;
}