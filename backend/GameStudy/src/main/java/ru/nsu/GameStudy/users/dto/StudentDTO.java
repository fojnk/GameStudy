package ru.nsu.GameStudy.users.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import ru.nsu.GameStudy.authentication.dto.UserDTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Builder
@Getter
public class StudentDTO {
        Long id;
        UserDTO user;
        BigDecimal wallet;
        Integer experience;
        Integer activity;
        BlogDTO blog;
        @JsonProperty(value = "birth_date")
        @JsonFormat(pattern = "dd.MM.yyyy")
        Date birthDate;
        Integer age;
        @JsonProperty(value = "image_path")
        String imagePath;
        @JsonProperty(value = "achievement_ids")
        List<Long> achievementIds;
        @JsonProperty(value = "course_ids")
        List<Long> courseIds;
        @JsonProperty(value = "group_ids")
        List<Long> groupIds;
}
