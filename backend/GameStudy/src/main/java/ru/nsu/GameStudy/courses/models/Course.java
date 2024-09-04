package ru.nsu.GameStudy.courses.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.nsu.GameStudy.authentication.models.User;
import ru.nsu.GameStudy.course_attributes.models.CourseTag;
import ru.nsu.GameStudy.gamification.models.ScoresBoard;
import ru.nsu.GameStudy.users.models.Student;
import ru.nsu.GameStudy.users.models.StudentGroup;
import ru.nsu.GameStudy.users.models.Teacher;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "cost")
    private BigDecimal cost;
    @Column(name = "rating")
    private BigDecimal rating;
    @Column(name = "complexity")
    private Integer complexity;
    @Column(name = "amt_passed_users")
    private Integer amtPassedUsers;
    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;
    @Column(name = "end_date")
    private Timestamp endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_uid", referencedColumnName = "id")
    private User creator;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST})
    @JoinTable(name = "courses_tags",
            uniqueConstraints = { @UniqueConstraint(columnNames = {"course_id", "tag_id"})},
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @Fetch(FetchMode.JOIN)
    private List<CourseTag> tags;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(name = "courses_lessons",
            uniqueConstraints = { @UniqueConstraint(columnNames = {"course_id", "lesson_id"})},
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id"))
    @Fetch(FetchMode.JOIN)
    private List<Lesson> lessons;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(name = "courses_tasks",
            uniqueConstraints = { @UniqueConstraint(columnNames = {"course_id", "task_id"})},
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    @Fetch(FetchMode.JOIN)
    private List<Task> tasks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.JOIN)
    private List<ScoresBoard> boards;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST})
    @JoinTable(name = "courses_students",
            uniqueConstraints = { @UniqueConstraint(columnNames = {"course_id", "student_id"})},
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    @Fetch(FetchMode.JOIN)
    private List<Student> students;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST})
    @JoinTable(name = "courses_teachers",
            uniqueConstraints = { @UniqueConstraint(columnNames = {"course_id", "teacher_id"})},
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    @Fetch(FetchMode.JOIN)
    private List<Teacher> teachers;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST})
    @JoinTable(name = "courses_groups",
            uniqueConstraints = { @UniqueConstraint(columnNames = {"course_id", "group_id"})},
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    @Fetch(FetchMode.JOIN)
    private List<StudentGroup> groups;
}