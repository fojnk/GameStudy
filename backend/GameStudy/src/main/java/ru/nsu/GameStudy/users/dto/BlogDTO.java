package ru.nsu.GameStudy.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BlogDTO {
        Long id;
        @JsonProperty("title")
        String title;
        @JsonProperty("text")
        String text;
}