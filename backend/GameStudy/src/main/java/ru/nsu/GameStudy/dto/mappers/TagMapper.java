package ru.nsu.GameStudy.dto.mappers;

import ru.nsu.GameStudy.dto.TagDTO;
import ru.nsu.GameStudy.models.Tag;

public class TagMapper {
    public static TagDTO toDTO(Tag tag) {
        return new TagDTO(
                tag.getId(),
                tag.getTitle()
        );
    }
}