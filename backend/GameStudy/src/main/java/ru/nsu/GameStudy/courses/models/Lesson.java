package ru.nsu.GameStudy.courses.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.nsu.GameStudy.course_attributes.models.Discipline;
import ru.nsu.GameStudy.gamification.models.GameMethod;
import ru.nsu.GameStudy.users.models.StudentGroup;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "experience")
    private Integer experience;
    @Column(name = "complexity")
    private Integer complexity;
    @Column(name = "video_path")
    private String videoPath;
    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "discipline_id", referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    private Discipline discipline;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST})
    @JoinTable(name = "lessons_tasks",
            uniqueConstraints = { @UniqueConstraint(columnNames = {"lesson_id", "task_id"})},
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    @Fetch(FetchMode.JOIN)
    private List<Task> tasks;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST})
    @JoinTable(name = "lessons_game_methods",
            uniqueConstraints = { @UniqueConstraint(columnNames = {"lesson_id", "game_method_id"})},
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "game_method_id"))
    @Fetch(FetchMode.JOIN)
    private List<GameMethod> gameMethods;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "lessons")
    @Fetch(FetchMode.JOIN)
    private List<Course> courses;
}