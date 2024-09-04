package ru.nsu.GameStudy.users.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import ru.nsu.GameStudy.authentication.dto.UserDTO;

import java.sql.Date;
import java.util.List;

@Builder
@Getter
public class TeacherRequest {
    Long id;
    String organisation;
    String qualification;
    @JsonProperty(value = "birth_date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    Date birthDate;
    Integer age;
    @JsonProperty(value = "image_path")
    String imagePath;
    @JsonProperty(value = "status_confirmed")
    Boolean statusConfirmed;
    Long user;
    Long blog;
    @JsonProperty(value = "course_ids")
    List<Long> courseIds;
}