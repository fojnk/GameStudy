package ru.nsu.GameStudy.courses.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.nsu.GameStudy.course_attributes.models.Discipline;
import ru.nsu.GameStudy.gamification.models.GameMethod;
import ru.nsu.GameStudy.users.models.StudentGroup;

import java.sql.Time;
import java.util.List;

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
    @Column(name="description", columnDefinition = "text")
    private String description;
    @Column(name="time")
    private Time time;
    @Column(name="experience")
    private Integer experience;
    @Column(name = "complexity")
    private Integer complexity;
    @Column(name = "video_path")
    private String videoPath;
    @Column(name="answer")
    private String answer;

    @ManyToOne
    @JoinColumn(name="discipline_id", referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    private Discipline discipline;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST})
    @JoinTable(name = "tasks_game_methods",
            uniqueConstraints = { @UniqueConstraint(columnNames = {"task_id", "game_method_id"})},
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "game_method_id"))
    @Fetch(FetchMode.JOIN)
    private List<GameMethod> gameMethods;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tasks")
    @Fetch(FetchMode.JOIN)
    private List<Course> courses;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tasks")
    @Fetch(FetchMode.JOIN)
    private List<Lesson> lessons;
}