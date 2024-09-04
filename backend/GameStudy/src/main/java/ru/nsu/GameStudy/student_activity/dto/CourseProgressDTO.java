package ru.nsu.GameStudy.student_activity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public class CourseProgressDTO {
        Long id;
        @JsonProperty(value = "reached_exp")
        BigDecimal reachedExp;
        @JsonProperty(value = "course_id")
        Long courseId;
        @JsonProperty(value = "student_id")
        Long studentId;
        @JsonProperty(value = "passed_task_ids")
        List<Long> passedTaskIds;
        @JsonProperty(value = "passed_lesson_ids")
        List<Long> passedLessonIds;
}
