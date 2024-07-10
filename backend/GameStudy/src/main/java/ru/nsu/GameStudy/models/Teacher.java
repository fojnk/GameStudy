package ru.nsu.GameStudy.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

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
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userData;
    @Column(name = "organisation")
    private String organisation;
    @Column(name = "qualification")
    private String qualification;
    @OneToOne
    @JoinColumn(name = "blog_id", referencedColumnName = "id")
    private Blog blog;
    @Column(name = "bth_date")
    private Date birthDate;
    @Column(name = "age")
    private Integer age;
    @Column(name = "image_path")
    private String imagePath;
}
