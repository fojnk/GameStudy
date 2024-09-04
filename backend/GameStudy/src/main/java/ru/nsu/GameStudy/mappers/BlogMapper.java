package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.users.dto.BlogDTO;
import ru.nsu.GameStudy.users.models.Blog;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class BlogMapper {
    public abstract BlogDTO toDTO(Blog blog);

    @Mapping(target = "id", ignore = true)
    public abstract void updateBlogFromDTO(
            @MappingTarget Blog blog, BlogDTO blogDTO);
}