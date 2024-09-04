package ru.nsu.GameStudy.gamification.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.nsu.GameStudy.course_attributes.models.CourseTag;
import ru.nsu.GameStudy.course_attributes.models.GameTag;
import ru.nsu.GameStudy.courses.models.Course;
import ru.nsu.GameStudy.courses.models.Lesson;
import ru.nsu.GameStudy.courses.models.Task;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game_methods")
public class GameMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", unique = true)
    private String title;
    @Column(name = "type")
    private String type;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST})
    @JoinTable(name = "game_methods_tags",
            uniqueConstraints = { @UniqueConstraint(columnNames = {"game_method_id", "tag_id"})},
            joinColumns = @JoinColumn(name = "game_method_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @Fetch(FetchMode.JOIN)
    private List<GameTag> tags;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "gameMethods")
    @Fetch(FetchMode.JOIN)
    private List<Lesson> lessons;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "gameMethods")
    @Fetch(FetchMode.JOIN)
    private List<Task> tasks;
}