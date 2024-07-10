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
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userData;
    @Column(name = "wallet")
    private BigDecimal wallet;
    @Column(name = "experience")
    private Integer experience;
    @Column(name = "activity")
    private Integer activity;
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
