package ru.nsu.GameStudy.users.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.nsu.GameStudy.authentication.models.User;
import ru.nsu.GameStudy.courses.models.Course;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "groups")
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_uid", referencedColumnName = "id")
    private User creator;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "groups_students",
            uniqueConstraints = { @UniqueConstraint(columnNames = {"group_id", "student_id"})},
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    @Fetch(FetchMode.JOIN)
    private List<Student> students;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
    @Fetch(FetchMode.JOIN)
    private List<Course> courses;
}
