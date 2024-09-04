package ru.nsu.GameStudy.student_activity.models;

import jakarta.persistence.*;
import lombok.*;
import ru.nsu.GameStudy.users.models.Student;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class ActivityRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="experience")
    private Integer experience;
    @Column(name="date")
    private Date date;
    @Column(name="time")
    private Time time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id", referencedColumnName = "id")
    private Student student;
}
