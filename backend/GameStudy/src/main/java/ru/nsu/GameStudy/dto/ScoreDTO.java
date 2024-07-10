package ru.nsu.GameStudy.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import ru.nsu.GameStudy.models.Course;
import ru.nsu.GameStudy.models.Student;

public class ScoreDTO {
    private Long id;
    private String title;

    private Student student;

    private Course course;

    private Integer experience;
}