package ru.nsu.GameStudy.gamification.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.nsu.GameStudy.users.models.Student;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "achievements")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", unique = true)
    private String title;
    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "required_exp")
    private Integer requiredExp;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "students_achievements",
            uniqueConstraints = { @UniqueConstraint(columnNames = {"achievement_id", "student_id"})},
            joinColumns = @JoinColumn(name = "achievement_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    @Fetch(FetchMode.JOIN)
    private List<Student> students;
}