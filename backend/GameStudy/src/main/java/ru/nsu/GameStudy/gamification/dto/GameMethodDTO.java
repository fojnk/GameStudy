package ru.nsu.GameStudy.gamification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GameMethodDTO {
        Integer id;
        String title;
        @JsonProperty("type")
        String type;
        @JsonProperty("tag_ids")
        List<Long> tagIds;
}