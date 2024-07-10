package ru.nsu.GameStudy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    private Long id;
    @JsonProperty(value = "user_id")
    private Long userId;
    private String organisation;
    private String qualification;
    private BlogDTO blog;
    @JsonProperty(value = "birth_date")
    private Date birthDate;
}