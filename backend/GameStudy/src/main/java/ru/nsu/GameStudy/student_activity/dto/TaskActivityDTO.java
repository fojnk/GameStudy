package ru.nsu.GameStudy.student_activity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import ru.nsu.GameStudy.student_activity.models.ActivityType;

import java.sql.Date;
import java.sql.Time;

@Builder
@Getter
public class TaskActivityDTO {
    Long id;
    Integer experience;
    Date date;
    Time time;
    @JsonProperty(value = "task_id")
    Long taskId;
    @JsonProperty(value = "student_id")
    Long studentId;
    ActivityType activity;
}
