package ru.nsu.GameStudy.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "creator_uid", referencedColumnName = "id")
    private User creator;
    @Column(name = "cost")
    private BigDecimal cost;
    @Column(name = "rating")
    private BigDecimal rating;
    @Column(name = "amt_passed_users")
    private Integer amtPassedUsers;
}