package ru.nsu.GameStudy.course_attributes.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.nsu.GameStudy.courses.models.Course;
import ru.nsu.GameStudy.gamification.models.Achievement;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course_tags")
public class CourseTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", unique = true)
    private String title;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    @Fetch(FetchMode.JOIN)
    private List<Course> courses;
}