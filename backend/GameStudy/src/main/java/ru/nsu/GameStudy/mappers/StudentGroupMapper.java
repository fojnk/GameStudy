package ru.nsu.GameStudy.mappers;

import org.mapstruct.*;
import ru.nsu.GameStudy.users.dto.StudentGroupDTO;
import ru.nsu.GameStudy.users.dto.StudentGroupRequest;
import ru.nsu.GameStudy.users.models.StudentGroup;

@Mapper(componentModel = "spring", uses = {
        ReferenceMapper.class, StudentMapper.class
},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class StudentGroupMapper {
    @Mapping(target = "creatorUId", source = "group.creator")
    public abstract StudentGroupDTO toDTO(StudentGroup group);

    @Mapping(target = "creator", source = "dto.creatorUId")
    @Mapping(target = "id", ignore = true)
    public abstract void updateGroupFromDTO(
            @MappingTarget StudentGroup group,
            StudentGroupRequest dto);
}
