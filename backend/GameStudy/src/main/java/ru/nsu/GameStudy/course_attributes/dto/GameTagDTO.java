package ru.nsu.GameStudy.course_attributes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GameTagDTO {
    Long id;
    @JsonProperty(value = "title")
    String title;
}
