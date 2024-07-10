package ru.nsu.GameStudy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.GameStudy.models.Discipline;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    @JsonProperty(value="discipline_id")
    private Integer disciplineId;
    private Time time;
    private Integer experience;
    private Integer complexity;
    private String answer;
}