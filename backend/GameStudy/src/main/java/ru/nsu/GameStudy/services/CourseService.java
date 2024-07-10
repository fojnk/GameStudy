package ru.nsu.GameStudy.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.dto.CourseDTO;
import ru.nsu.GameStudy.models.Course;
import ru.nsu.GameStudy.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserService userService;

    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("course not found"));
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getAllCoursesByRatingInRange(Double minRate, Double maxRate) {
        return courseRepository.findByRatingInRange(minRate, maxRate)
                .orElseThrow(() -> new NotFoundException("courses with this rating range not found"));
    }

    public void deleteCourse(Long courseId) {
        Course course = getCourse(courseId);

        courseRepository.delete(course);
    }

    public Course updateCourse(Long courseId, CourseDTO request) {
        Course course = getCourse(courseId);

        course.setAmtPassedUsers(request.getAmtPassedUsers());
        course.setCost(request.getCost());
        course.setDescription(request.getDescription());
        course.setRating(request.getRating());
        course.setTitle(request.getTitle());

        return courseRepository.save(course);
    }

    public Optional<List<Course>> getStudentCourses(Long studentId) {
        return courseRepository.findByStudentId(studentId);
    }

    public Course createCourse(CourseDTO request) {
        var creator = userService.getUserByUserId(request.getCreatorUId());

        Course course = Course.builder()
                .creator(creator)
                .title(request.getTitle())
                .amtPassedUsers(request.getAmtPassedUsers())
                .description(request.getDescription())
                .rating(request.getRating())
                .cost(request.getCost())
                .build();

        return courseRepository.save(course);
    }

    public Boolean addLessonToCourse(Integer courseId, Integer lessonId) {
        return false;
    }

    public void deleteLessonFromCourse(Integer courseId, Integer lessonId) {}
}