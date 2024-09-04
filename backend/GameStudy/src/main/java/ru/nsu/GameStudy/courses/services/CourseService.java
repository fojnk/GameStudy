package ru.nsu.GameStudy.courses.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.authentication.dto.UserDTO;
import ru.nsu.GameStudy.course_attributes.dto.CourseTagDTO;
import ru.nsu.GameStudy.courses.dto.CourseDTO;
import ru.nsu.GameStudy.courses.dto.CourseRequest;
import ru.nsu.GameStudy.courses.dto.LessonDTO;
import ru.nsu.GameStudy.courses.dto.TaskDTO;
import ru.nsu.GameStudy.courses.models.Course;
import ru.nsu.GameStudy.courses.models.Lesson;
import ru.nsu.GameStudy.courses.models.Task;
import ru.nsu.GameStudy.courses.repositories.CourseRepository;
import ru.nsu.GameStudy.mappers.*;
import ru.nsu.GameStudy.student_activity.dto.CourseProgressDTO;
import ru.nsu.GameStudy.student_activity.services.CourseProgressService;
import ru.nsu.GameStudy.users.dto.StudentDTO;
import ru.nsu.GameStudy.users.dto.StudentGroupDTO;
import ru.nsu.GameStudy.users.dto.TeacherDTO;
import ru.nsu.GameStudy.users.models.Student;
import ru.nsu.GameStudy.users.models.StudentGroup;
import ru.nsu.GameStudy.users.models.Teacher;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserMapper userMapper;
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final LessonService lessonService;
    private final LessonMapper lessonMapper;
    private final CourseTagService tagService;
    private final CourseTagMapper tagMapper;
    private final StudentService studentService;
    private final StudentMapper studentMapper;
    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;
    private final StudentGroupService groupService;
    private final StudentGroupMapper groupMapper;
    private final CourseProgressService progressService;

    public CourseDTO getCourseDTO(Long courseId) {
        return courseMapper.toDTO(getCourse(courseId));
    }
    public List<CourseDTO> getAllCourses() {
        log.info("GET all courses");
        return courseRepository.findAll().stream().map(courseMapper::toDTO).toList();
    }

    public List<CourseDTO> getAllCoursesByRatingInRange(Double minRate, Double maxRate) {
        log.info("GET courses where {} <= rating < {}", minRate, maxRate);
        List<Course> courses = courseRepository.findByRatingBetween(minRate, maxRate);
        if (courses == null) {
            log.error("no course within rating range found");
            throw new NotFoundException("courses within this rating range not found");
        }
        return courses.stream().map(courseMapper::toDTO).toList();
    }

    public List<CourseDTO> getAllCoursesByCostInRange(BigDecimal minCost, BigDecimal maxCost) {
        log.info("GET courses where {} <= rating < {}", minCost, maxCost);
        List<Course> courses = courseRepository.findByCostBetween(minCost, maxCost);
        if (courses == null) {
            log.error("no course within cost range found");
            throw new NotFoundException("courses within this cost range not found");
        }
        return courses.stream().map(courseMapper::toDTO).toList();
    }

    public List<CourseDTO> getCoursesByCreator(Long creatorId) {
        log.info("GET courses with creator {}", creatorId);
        List<Course> courses = courseRepository.findByCreator_id(creatorId);
        if (courses == null) {
            log.error("no course by this author found");
            throw new NotFoundException("courses by this author not found");
        }
        return courses.stream().map(courseMapper::toDTO).toList();
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        log.info("DELETE course with id: {}", courseId);
        if (courseRepository.existsById(courseId)) {
            courseRepository.deleteById(courseId);
        } else {
            log.info("course with id: {} not found", courseId);
            throw new NotFoundException("course not found");
        }
    }

    @Transactional
    public CourseDTO updateCourse(Long courseId, CourseRequest request) {
        log.info("UPDATE course with id: {}, request: " +
                        "{passedUser {}, cost {}, description {}, rating {}}", courseId,
                request.getAmtPassedUsers(), request.getCost(), request.getDescription(), request.getRating());
        Course course = getCourse(courseId);

        courseMapper.updateCourseFromDTO(course, request);

        return save(course);
    }

    @Transactional
    public CourseDTO createCourse(CourseRequest request) {
        log.info("CREATE course with cost: {}, rating: {}, title: {}, discription: {}",
                request.getCost(), request.getRating(), request.getTitle(), request.getDescription());
        Course course = Course.builder()
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        courseMapper.updateCourseFromDTO(course, request);

        return save(course);
    }

    public UserDTO getCourseCreator(Long courseId) {
        log.info("GET course creator, course id: {}", courseId);
        Course course = getCourse(courseId);
        return userMapper.toDTO(course.getCreator());
    }

    public List<TaskDTO> getCourseTasks(Long courseId) {
        log.info("GET course: {} tasks", courseId);
        Course course = getCourse(courseId);
        return course.getTasks().stream().map(taskMapper::toDTO).toList();
    }

    @Transactional
    public CourseDTO addTaskToCourse(Long courseId, Long taskId) {
        log.info("ADD task {} to course: {}", taskId, courseId);
        Course course = getCourse(courseId);
        course.getTasks().add(taskService.getTask(taskId));
        return save(course);
    }

    @Transactional
    public CourseDTO removeTaskFromCourse(Long courseId, Long taskId) {
        log.info("REMOVE task {} from course: {}", taskId, courseId);
        Course course = getCourse(courseId);
        course.getTasks().remove(taskService.getTask(taskId));
        return save(course);
    }

    @Transactional
    public List<LessonDTO> getCourseLessons(Long courseId) {
        log.info("GET course: {} lessons", courseId);
        Course course = getCourse(courseId);
        return course.getLessons().stream().map(lessonMapper::toDTO).toList();
    }

    @Transactional
    public CourseDTO addLessonToCourse(Long courseId, Long lessonId) {
        log.info("ADD lesson {} to course: {}", lessonId, courseId);
        Course course = getCourse(courseId);
        Lesson lesson = lessonService.getLesson(lessonId);

        course.getLessons().add(lesson);
        for (Task t : lesson.getTasks()) {
            if (!course.getTasks().contains(t)) {
                course.getTasks().add(t);
            }
        }

        return save(course);
    }

    @Transactional
    public CourseDTO removeLessonFromCourse(Long courseId, Long lessonId) {
        log.info("REMOVE lesson {} from course: {}", lessonId, courseId);
        Course course = getCourse(courseId);
        course.getLessons().remove(lessonService.getLesson(lessonId));
        return save(course);
    }

    @Transactional
    public List<CourseTagDTO> getTagsInCourse(Long courseId) {
        log.info("GET course tags, course: {}", courseId);
        Course course = getCourse(courseId);
        return course.getTags().stream().map(tagMapper::toDTO).toList();
    }

    @Transactional
    public CourseDTO addTagToCourse(Long courseId, Long tagId) {
        log.info("ADD tag {} to course: {}", tagId, courseId);
        Course course = getCourse(courseId);
        course.getTags().add(tagService.getTag(tagId));
        return save(course);
    }

    @Transactional
    public CourseDTO removeTagFromCourse(Long courseId, Long tagId) {
        log.info("REMOVE tag {} from course: {}", tagId, courseId);
        Course course = getCourse(courseId);
        course.getTags().remove(tagService.getTag(tagId));
        return save(course);
    }

    @Transactional
    public List<CourseDTO> getCoursesByDiscipline(Long disciplineId) {
        log.info("GET courses with discipline {}", disciplineId);
        return courseRepository.findByLessons_Discipline_idAndTasks_Discipline_id(disciplineId, disciplineId)
                .stream()
                .map(courseMapper::toDTO)
                .toList();
    }

    @Transactional
    public List<CourseDTO> getCoursesWithTag(Long tagId) {
        log.info("GET courses with tag {}", tagId);
        return courseRepository
                .findByTags_id(tagId)
                .stream()
                .map(courseMapper::toDTO)
                .toList();
    }

    public List<StudentDTO> getStudentsOnCourse(Long courseId) {
        log.info("GET students on course {}", courseId);
        Course course = getCourse(courseId);
        return course
                .getStudents()
                .stream()
                .map(studentMapper::toDTO)
                .toList();
    }

    @Transactional
    public CourseDTO addStudentsToCourse(Long courseId, List<Long> studentIds) {
        log.info("ADD students {} to course {}", studentIds, courseId);
        Course course = getCourse(courseId);
        List<Long> exceptIds = course.getStudents().stream().map(Student::getId).toList();
        List<Long> ids = studentIds
                .stream()
                .filter(x ->
                        !exceptIds.contains(x))
                .toList();

        for (Long id : ids) {
            CourseProgressDTO dto = CourseProgressDTO.builder()
                    .courseId(course.getId())
                    .studentId(id)
                    .reachedExp(BigDecimal.valueOf(0))
                    .build();
            progressService.createCourseProgress(dto);
        }

        course
                .getStudents()
                .addAll(ids.stream().map(studentService::getStudent).toList());
        return save(course);
    }

    @Transactional
    public CourseDTO removeStudentsFromCourse(Long courseId, List<Long> studentIds) {
        log.info("REMOVE students {} from course {}", studentIds, courseId);
        Course course = getCourse(courseId);
        course
                .getStudents()
                .removeAll(studentIds.stream().map(studentService::getStudent).toList());

        for (Long studentId : studentIds) {
            progressService
                    .deleteCourseProgress(progressService.getProgressByCourseAndStudent(courseId, studentId).getId());
        }
        return save(course);
    }

    @Transactional
    public List<TeacherDTO> getTeachersOnCourse(Long courseId) {
        log.info("GET course: {} teachers", courseId);
        Course course = getCourse(courseId);
        return course
                .getTeachers()
                .stream()
                .map(teacherMapper::toDTO)
                .toList();
    }

    @Transactional
    public CourseDTO addTeachersToCourse(Long courseId, List<Long> teacherIds) {
        log.info("ADD teachers {} to course {}", teacherIds, courseId);
        Course course = getCourse(courseId);
        List<Long> exceptIds = course.getTeachers().stream().map(Teacher::getId).toList();
        List<Long> ids = teacherIds
                .stream()
                .filter(x ->
                        !exceptIds.contains(x))
                .toList();
        course
                .getTeachers()
                .addAll(ids.stream().map(teacherService::getTeacher).toList());
        return save(course);
    }

    @Transactional
    public CourseDTO removeTeachersFromCourse(Long courseId, List<Long> teacherIds) {
        log.info("REMOVE teachers {} from course {}", teacherIds, courseId);
        Course course = getCourse(courseId);
        course
                .getTeachers()
                .removeAll(teacherIds.stream().map(teacherService::getTeacher).toList());
        return save(course);
    }

    @Transactional
    public List<StudentGroupDTO> getGroupsOnCourse(Long courseId) {
        log.info("GET course: {} groups", courseId);
        Course course = getCourse(courseId);
        return course
                .getGroups()
                .stream()
                .map(groupMapper::toDTO)
                .toList();
    }

    @Transactional
    public CourseDTO addGroupsToCourse(Long courseId, List<Long> groupIds) {
        log.info("ADD groups {} to course {}", groupIds, courseId);
        Course course = getCourse(courseId);
        List<Long> exceptIds = course.getGroups().stream().map(StudentGroup::getId).toList();
        List<Long> ids = groupIds
                .stream()
                .filter(x ->
                        !exceptIds.contains(x))
                .toList();
        course
                .getGroups()
                .addAll(ids.stream().map(groupService::getStudentGroup).toList());

        groupIds
                .forEach(x -> {
                    addStudentsToCourse(
                            courseId,
                            groupService.getStudentsInGroup(x).stream().map(StudentDTO::getId).toList()
                    );
                });

        return save(course);
    }

    @Transactional
    public CourseDTO removeGroupsFromCourse(Long courseId, List<Long> groupIds) {
        log.info("REMOVE groups {} from course {}", groupIds, courseId);
        Course course = getCourse(courseId);
        course
                .getGroups()
                .removeAll(groupIds.stream().map(groupService::getStudentGroup).toList());
        groupIds
                .forEach(x -> {
                    removeStudentsFromCourse(
                            courseId,
                            groupService.getStudentsInGroup(x).stream().map(StudentDTO::getId).toList()
                    );
                });
        return save(course);
    }

    protected CourseDTO save(Course course) {
        return courseMapper.toDTO(courseRepository.save(course));
    }

    protected Course getCourse(Long id) {
        log.info("GET course with id: {}", id);
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            log.error("course with id: {} not found", id);
            throw new NotFoundException("course not found");
        }
        else {
            return course.get();
        }
    }
}
