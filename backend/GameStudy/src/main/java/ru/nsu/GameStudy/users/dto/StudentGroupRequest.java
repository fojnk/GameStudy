package ru.nsu.GameStudy.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class StudentGroupRequest {
    Long id;
    String title;
    @JsonProperty(value = "creator_uid")
    Long creatorUId;
    List<Long> students;
}
