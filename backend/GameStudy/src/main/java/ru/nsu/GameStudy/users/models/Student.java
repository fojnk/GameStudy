package ru.nsu.GameStudy.users.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.nsu.GameStudy.authentication.models.User;
import ru.nsu.GameStudy.courses.models.Course;
import ru.nsu.GameStudy.gamification.models.Achievement;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "wallet")
    private BigDecimal wallet;
    @Column(name = "experience")
    private Integer experience;
    @Column(name = "activity")
    private Integer activity;
    @Column(name = "bth_date")
    private Date birthDate;
    @Column(name = "image_path")
    private String imagePath;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true, referencedColumnName = "id")
    private User userData;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "blog_id", unique = true, referencedColumnName = "id")
    private Blog blog;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "students")
    @Fetch(FetchMode.JOIN)
    private List<Course> courses;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "students")
    @Fetch(FetchMode.JOIN)
    private List<Achievement> achievements;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "students")
    @Fetch(FetchMode.JOIN)
    private List<StudentGroup> groups;
}
