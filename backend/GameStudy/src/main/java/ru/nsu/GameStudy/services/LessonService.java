package ru.nsu.GameStudy.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.dto.LessonDTO;
import ru.nsu.GameStudy.models.Discipline;
import ru.nsu.GameStudy.models.Lesson;
import ru.nsu.GameStudy.models.Task;
import ru.nsu.GameStudy.repository.DisciplineRepository;
import ru.nsu.GameStudy.repository.LessonRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final DisciplineService disciplineService;

    public Lesson getLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundException("lesson not found"));
    }

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public List<Lesson> getLessonsInCourse(Long courseId) {
        return lessonRepository.findByCourseId(courseId)
                .orElseThrow(() -> new NotFoundException("lessons not found"));
    }

    public void deleteLesson(Long lessonId) {
        Lesson lesson = getLesson(lessonId);
        lessonRepository.delete(lesson);
    }

    public Lesson updateLesson(Long lessonId, LessonDTO request) {
        Lesson lesson = getLesson(lessonId);

        lesson.setComplexity(request.getComplexity());
        lesson.setTitle(request.getTitle());
        lesson.setDescription(request.getDescription());
        lesson.setExperience(request.getExperience());

        return lessonRepository.save(lesson);
    }

    public Lesson createLesson(LessonDTO request) {
        var discipline = disciplineService.getDiscipline(request.getDisciplineId());

        Lesson lesson = Lesson.builder()
                .title(request.getTitle())
                .complexity(request.getComplexity())
                .experience(request.getExperience())
                .discipline(discipline)
                .build();

        return lessonRepository.save(lesson);
    }

    public Boolean addTaskToLesson(Task task) {return false;}

    public void deleteTaskFromLesson(Integer lessonId, Integer taskId) {}
}