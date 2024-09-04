package ru.nsu.GameStudy.users.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.nsu.GameStudy.courses.models.Course;
import ru.nsu.GameStudy.authentication.models.User;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "organisation")
    private String organisation;
    @Column(name = "qualification")
    private String qualification;
    @Column(name = "bth_date")
    private Date birthDate;
    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "status_confirmed")
    private Boolean statusConfirmed;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true, referencedColumnName = "id")
    private User userData;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "blog_id", unique = true, referencedColumnName = "id")
    private Blog blog;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "teachers")
    @Fetch(FetchMode.JOIN)
    private List<Course> courses;
}
