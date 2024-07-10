package ru.nsu.GameStudy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameMethodDTO {
    private Integer id;
    private String title;
    private String type;
}