package ru.nsu.GameStudy.student_activity.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.nsu.GameStudy.courses.models.Course;
import ru.nsu.GameStudy.courses.models.Lesson;
import ru.nsu.GameStudy.courses.models.Task;
import ru.nsu.GameStudy.users.models.Student;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course_progress",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
public class CourseProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "reached_exp")
    private BigDecimal reachedExp;

    @ManyToOne
    @JoinColumn(name="course_id", referencedColumnName = "id")
    private Course course;

    @ManyToOne
    @JoinColumn(name="student_id", referencedColumnName = "id")
    private Student student;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "passed_task_ids", referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    private List<Task> passedTasks;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "passed_lesson_ids", referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    private List<Lesson> passedLessons;
}
