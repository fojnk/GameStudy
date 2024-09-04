package ru.nsu.GameStudy.course_attributes.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DisciplineDTO {
    Long id;
    String title;
}