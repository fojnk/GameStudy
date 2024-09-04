package ru.nsu.GameStudy.courses.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Builder
@Getter
public class CourseDTO {
        Long id;
        String title;
        String description;
        BigDecimal cost;
        BigDecimal rating;
        Integer complexity;
        @JsonProperty(value = "amt_passed_users")
        Integer amtPassedUsers;
        @JsonProperty(value = "image_path")
        String imagePath;
        @JsonProperty(value = "created_at")
        @JsonFormat(pattern = "HH:mm:ss dd.MM.yyyy")
        Timestamp createdAt;
        @JsonFormat(pattern = "HH:mm:ss dd.MM.yyyy")
        Timestamp endDate;
        @JsonProperty(value = "creator_uid")
        Long creatorUId;
        @JsonProperty(value = "tag_ids")
        List<Long> tagIds;
        @JsonProperty(value = "lesson_ids")
        List<Long> lessonIds;
        @JsonProperty(value = "task_ids")
        List<Long> taskIds;
        @JsonProperty(value = "board_ids")
        List<Long> boardIds;
        @JsonProperty(value = "teacher_ids")
        List<Long> teacherIds;
        @JsonProperty(value = "student_ids")
        List<Long> studentIds;
        @JsonProperty(value = "group_ids")
        List<Long> groupIds;
}