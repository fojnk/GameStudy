package ru.nsu.GameStudy.dto.mappers;

import ru.nsu.GameStudy.dto.BlogDTO;
import ru.nsu.GameStudy.models.Blog;

public class BlogMapper {
    public static BlogDTO toDTO(Blog blog) {
        return new BlogDTO(
                blog.getId(),
                blog.getTitle(),
                blog.getText()
        );
    }
}