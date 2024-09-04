package ru.nsu.GameStudy.mappers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.nsu.GameStudy.authentication.dto.UserDTO;
import ru.nsu.GameStudy.authentication.models.User;
import ru.nsu.GameStudy.course_attributes.dto.DisciplineDTO;
import ru.nsu.GameStudy.courses.dto.TaskDTO;
import ru.nsu.GameStudy.course_attributes.models.Discipline;
import ru.nsu.GameStudy.courses.models.Task;
import ru.nsu.GameStudy.gamification.dto.GameMethodDTO;
import ru.nsu.GameStudy.gamification.models.GameMethod;
import ru.nsu.GameStudy.users.dto.BlogDTO;
import ru.nsu.GameStudy.users.models.Blog;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

@Component
@RequiredArgsConstructor
@Named("MapUtils")
public class MapUtils {
    @PersistenceContext
    private final EntityManager entityManager;

    @Named("disciplineFromDTO")
    protected Discipline DTOToDiscipline(DisciplineDTO dto) {
        return entityManager.find(Discipline.class, dto.getId());
    }

    @Named("blogFromDTO")
    protected Blog DTOToBlog(BlogDTO dto) {
        return entityManager.find(Blog.class, dto.getId());
    }

    @Named("userFromDTO")
    protected User DTOToUser(UserDTO dto) {
        return entityManager.find(User.class, dto.getId());
    }

    @Named("taskFromDTO")
    protected Task DTOToTask(TaskDTO dto) {
        return entityManager.find(Task.class, dto.getId());
    }

    @Named("gameMethodFromDTO")
    protected GameMethod DTOToGameMethod(GameMethodDTO dto) {
        return entityManager.find(GameMethod.class, dto.getId());
    }

    @Named("ageFromDate")
    public Integer calculateAge(Date birthDate) {
        if (birthDate == null) {
            return 0;
        }
        return Period.between(
                        birthDate.toLocalDate(),
                        Date.valueOf(LocalDate.now()).toLocalDate())
                .getYears();
    }
}


