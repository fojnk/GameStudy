package ru.nsu.GameStudy.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.nsu.GameStudy.authentication.dto.NotificationDTO;
import ru.nsu.GameStudy.authentication.dto.UserDTO;
import ru.nsu.GameStudy.authentication.models.Role;
import ru.nsu.GameStudy.course_attributes.dto.CourseTagDTO;
import ru.nsu.GameStudy.course_attributes.dto.GameTagDTO;
import ru.nsu.GameStudy.course_attributes.services.DisciplineService;
import ru.nsu.GameStudy.course_attributes.services.CourseTagService;
import ru.nsu.GameStudy.course_attributes.services.GameTagService;
import ru.nsu.GameStudy.courses.dto.*;
import ru.nsu.GameStudy.courses.services.CourseService;
import ru.nsu.GameStudy.courses.services.LessonService;
import ru.nsu.GameStudy.courses.services.TaskService;
import ru.nsu.GameStudy.gamification.dto.AchievementDTO;
import ru.nsu.GameStudy.gamification.services.AchievementService;
import ru.nsu.GameStudy.gamification.services.ScoresBoardService;
import ru.nsu.GameStudy.course_attributes.dto.DisciplineDTO;
import ru.nsu.GameStudy.exceptions.UserAlreadyExistsException;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;
import ru.nsu.GameStudy.gamification.dto.GameMethodDTO;
import ru.nsu.GameStudy.gamification.services.GameMethodService;
import ru.nsu.GameStudy.authentication.dto.requests.RegisterRequest;
import ru.nsu.GameStudy.authentication.dto.response.AuthResponse;
import ru.nsu.GameStudy.authentication.services.AuthService;
import ru.nsu.GameStudy.authentication.services.JwtService;
import ru.nsu.GameStudy.authentication.services.NotificationService;
import ru.nsu.GameStudy.authentication.services.UserService;
import ru.nsu.GameStudy.users.dto.*;
import ru.nsu.GameStudy.users.services.BlogService;
import ru.nsu.GameStudy.users.services.StudentGroupService;
import ru.nsu.GameStudy.users.services.StudentService;
import ru.nsu.GameStudy.users.services.TeacherService;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
@RequiredArgsConstructor
public class TestDataInserter implements ApplicationListener<ApplicationReadyEvent> {
    private final AuthService authService;
    private final JwtService jwtService;
    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final BlogService blogService;
    private final LessonService lessonService;
    private final Random random = new Random();
    private final CourseService courseService;
    private final CourseTagService courseTagService;
    private final GameTagService gameTagService;
    private final TaskService taskService;
    private final DisciplineService disciplineService;
    private final NotificationService notificationService;
    private final AchievementService achievementService;
    private final StudentGroupService groupService;
    private final ScoresBoardService boardService;
    private final GameMethodService methodService;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        int teachersCount = 5;
        List<UserDTO> userDTOList = new ArrayList<>();
        for (int i = 0; i < teachersCount; i++) {
            UserDTO user = null;
            try {
                user = insertRandomUser(Role.TEACHER, "teacher" + i +"@gmail.com", "8 800 55 35 5" + i, i);
            } catch (UserAlreadyExistsException | ValueAlreadyExistsException | ParseException e) {
                throw new RuntimeException(e);
            }
            userDTOList.add(user);

            var teacher = teacherService.getTeacherByUser(user.getId());
            BlogDTO blog = insertRandomBlog(
                    teacher.getBlog().getId(),
                    "Блог " + teacher.getUser().getName());
            Date date = Date.valueOf(LocalDate.now()
                    .minusYears(random.nextInt(22, 65))
                    .minusDays(random.nextInt(0, 366)));
            TeacherRequest teacherDTO = TeacherRequest.builder()
                    .birthDate(date)
                    .build();
            teacherService.updateTeacher(teacher.getId(), teacherDTO);
        }
        log.info("teachers done");

        int studentsCount = 15;
        for (int i = 0; i < studentsCount; i++) {
            UserDTO user = null;
            try {
                user = insertRandomUser(Role.STUDENT, "student" + i +"@gmail.com", "8 800 55 35 5 " + i, i);
            } catch (UserAlreadyExistsException | ValueAlreadyExistsException | ParseException e) {
                throw new RuntimeException(e);
            }
            userDTOList.add(user);

            var student = studentService.getStudentByUser(user.getId());
            BlogDTO blog = insertRandomBlog(
                    student.getBlog().getId(),
                    "Блог " + student.getUser().getName());
            Date date = Date.valueOf(LocalDate.now()
                    .minusYears(random.nextInt(12, 18))
                    .minusDays(random.nextInt(0, 366)));
            StudentRequest studentDTO = StudentRequest.builder()
                    .birthDate(date)
                    .experience(random.nextInt(1, 200))
                    .build();
            studentService.updateStudent(student.getId(), studentDTO);

        }
        log.info("students done");

        int courseTagsCount = 5;
        List<CourseTagDTO> courseTagDTOList = new ArrayList<>();
        for (int i = 0; i < courseTagsCount; i++) {
            CourseTagDTO tagDTO = null;
            try {
                tagDTO = insertRandomCourseTag("Tag " + i);
            } catch (ValueAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
            courseTagDTOList.add(tagDTO);
        }
        List<GameTagDTO> gameTagList = new ArrayList<>();
        for (int i = 0; i < courseTagsCount; i++) {
            GameTagDTO tagDTO = null;
            try {
                tagDTO = insertRandomGameTag("Tag " + i);
            } catch (ValueAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
            gameTagList.add(tagDTO);
        }
        log.info("tags done");

        int disciplinesCount = 8;
        for (int i = 1; i <= disciplinesCount; i++) {
            try {
                insertRandomDiscipline("Дисциплина " + i);
            } catch (ValueAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("disciplines done");

        int gameMethodsCount = 3;
        for (int i = 1; i <= gameMethodsCount; i++) {
            try {
                GameMethodDTO methodDTO = insertRandomGameMethod("Метод Геймификации " + i, "Тип " + i);
            } catch (ValueAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }
        methodService.addTagsToGameMethod(1L, List.of(1L, 2L));
        methodService.addTagsToGameMethod(2L, List.of(1L, 3L));
        methodService.addTagsToGameMethod(3L, List.of(3L));
        log.info("game methods done");

        int tasksCount = 8;
        List<Long> disciplineToTask = List.of(1L, 2L, 2L, 3L, 4L, 5L, 7L, 8L);
        List<TaskDTO> taskDTOList = new ArrayList<>();
        for (int i = 0; i < tasksCount; i++) {
            taskDTOList.add(insertRandomTask((long) i));
        }
        log.info("tasks done");

        int lessonsCount = 4;
        List<Long> disciplineToLesson = List.of(2L, 3L, 5L, 6L);
        for (int i = 0; i < lessonsCount; i++) {
            LessonDTO lessonDTO = insertRandomLesson((long) i);
        }
        lessonService.addTaskToLesson(1L, 1L);
        lessonService.addTaskToLesson(1L, 2L);
        lessonService.addTaskToLesson(2L, 3L);
        lessonService.addTaskToLesson(2L, 4L);
        lessonService.addTaskToLesson(3L, 5L);
        lessonService.addTaskToLesson(3L, 6L);
        log.info("lessons done");

        List<LessonDTO> lessonDTOList = lessonService.getAllLessons();
        List<TeacherDTO> teacherDTOList = teacherService.getAllTeachers();
        List<StudentDTO> studentDTOList = studentService.getAllStudents();
        int coursesCount = 2;
        List<CourseDTO> courseDTOList = new ArrayList<>();
        for (int i = 0; i < coursesCount; i++) {
            CourseDTO courseDTO = insertRandomCourse((long) i + 1);

            for (var task: taskDTOList) {
                courseService.addTaskToCourse(courseDTO.getId(), task.getId());
            }

            for (var lessons: lessonDTOList) {
                courseService.addLessonToCourse(courseDTO.getId(), lessons.getId());
            }

            courseService.addStudentsToCourse(
                    courseDTO.getId(),
                    studentDTOList.stream().map(StudentDTO::getId).toList());

            courseDTOList.add(courseService.getCourseDTO(courseDTO.getId()));
        }
        log.info("courses done");

        int achievementsCount = 5;
        for (int i = 1; i <= achievementsCount; i++) {
            try {
                insertRandomAchievement("Достижение " + i);
            } catch (ValueAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }
        for (AchievementDTO a : achievementService.getAllAchievements()) {
            studentService.getAllStudents().stream().forEach(s -> {
                if (s.getExperience() >= a.getRequiredExp()) {
                    achievementService.addAchievementToStudent(a.getId(), s.getId());
                }
            });
        }
        log.info("achievements done");

        StudentGroupDTO groupDTO = groupService.createStudentGroup(StudentGroupRequest.builder()
                .title("Группа 1")
                .build());
        groupService.addStudentToGroup(groupDTO.getId(),  1L);
        groupService.addStudentToGroup(groupDTO.getId(),  2L);
        groupService.addStudentToGroup(groupDTO.getId(),  3L);
        groupService.addStudentToGroup(groupDTO.getId(),  4L);
        StudentGroupDTO groupDTO1 = groupService.createStudentGroup(StudentGroupRequest.builder()
                .title("Группа 2")
                .build());
        groupService.addStudentToGroup(groupDTO1.getId(), 6L);
        groupService.addStudentToGroup(groupDTO1.getId(), 7L);
        groupService.addStudentToGroup(groupDTO1.getId(), 8L);
        groupService.addStudentToGroup(groupDTO1.getId(), 9L);

        insertRandomNotification(userDTOList.get(0),
                studentDTOList.stream().map(StudentDTO::getId).toList());
        log.info("notifications done");
    }

    private UserDTO insertRandomUser(Role role, String email, String phoneNumber, Integer i)
            throws UserAlreadyExistsException, ValueAlreadyExistsException, ParseException {
        RegisterRequest request = RegisterRequest.builder()
                .name("Имя" + i)
                .surname("Фамилия" + i)
                .fathersName("Отчество" + i)
                .email(email)
                .password("123456")
                .phoneNumber(phoneNumber)
                .role(role)
                .build();
        AuthResponse response = authService.register(request);
        String username = jwtService.extractUsername(response.getAccess_token());
        return userService.getUserByEmail(username);
    }

    private BlogDTO insertRandomBlog(Long blogId, String title) {
        BlogDTO blogDTO = blogService.updateBlog(blogId, BlogDTO.builder()
                .title(title)
                .text("Lorem ipsum odor amet, consectetuer adipiscing elit. Phasellus consequat cursus netus vulputate dignissim vehicula. " +
                        "Ipsum egestas per iaculis non conubia condimentum ornare. Duis tempus lectus amet dictum adipiscing fames. " +
                        "Aptent molestie in id euismod sociosqu himenaeos conubia, himenaeos quam. " +
                        "Feugiat per litora mollis laoreet vulputate potenti. " +
                        "Integer vitae litora ridiculus facilisi habitasse netus. Neque id pulvinar ullamcorper dictum pellentesque ac diam cras. " +
                        "Fames in nunc condimentum; nascetur nam mauris fringilla. Ligula posuere eu curabitur natoque id netus.\n" +
                        "\n" +
                        "Cursus nunc commodo consectetur blandit vestibulum penatibus ultricies tempus. " +
                        "Senectus cras quam ante rutrum, nec aliquet pretium torquent. Sodales non sapien dis; sed pharetra praesent mollis. " +
                        "Molestie efficitur etiam venenatis taciti consequat dui. Conubia lorem neque adipiscing; ac feugiat dictum. " +
                        "Orci cras pulvinar lacus, class sed iaculis netus et. Tempus gravida ultrices pretium nulla potenti vulputate justo. " +
                        "Conubia integer bibendum a metus duis commodo ipsum.\n" +
                        "\n" +
                        "Tincidunt orci quis ornare ornare leo fames dapibus fermentum. Augue nascetur primis pretium pretium nulla montes suscipit. " +
                        "Quisque nisl ut fusce facilisis cursus platea pretium. Ornare nibh dui consectetur aenean conubia nullam facilisi massa. " +
                        "Sodales amet posuere ligula bibendum ultrices ad orci erat. Id magnis elit; ut tristique potenti vel porttitor nisi. " +
                        "Posuere sapien taciti ante vivamus blandit dui quisque! Torquent mauris suscipit tortor id lacus sem pulvinar. " +
                        "Elementum diam hendrerit placerat primis ipsum justo rhoncus.")
                .build());
        return blogDTO;
    }

    private CourseDTO insertRandomCourse(
            Long creatorUId) {
        CourseRequest courseDTO = CourseRequest.builder()
                .amtPassedUsers(random.nextInt(100))
                .cost(BigDecimal.valueOf(random.nextInt(1000)))
                .imagePath("https://devblogs.microsoft.com/python/wp-content/uploads/sites/12/2018/08/pythonfeature.png")
                .creatorUId(creatorUId)
                .description("Предлагаем вам наш уникальный курс по разработке на Python." +
                        "Всего за месяц вы научитесь всем основам программирования на этом языке." )
                .rating(BigDecimal.valueOf(random.nextInt(100)))
                .title("Введение в Python")
                .build();
        CourseDTO course = courseService.createCourse(courseDTO);
        return course;
    }

    private AchievementDTO insertRandomAchievement(String title) throws ValueAlreadyExistsException {
        AchievementDTO achievementDTO = AchievementDTO.builder()
                .title(title)
                .requiredExp(random.nextInt(2, 200))
                .build();
        return achievementService.createAchievement(achievementDTO);
    }

    private CourseTagDTO insertRandomCourseTag(String title) throws ValueAlreadyExistsException {
        CourseTagDTO tagDTO = CourseTagDTO.builder()
                .title(title)
                .build();
        return courseTagService.createTag(tagDTO);
    }

    private GameTagDTO insertRandomGameTag(String title) throws ValueAlreadyExistsException {
        GameTagDTO tagDTO = GameTagDTO.builder()
                .title(title)
                .build();
        return gameTagService.createTag(tagDTO);
    }

    private DisciplineDTO insertRandomDiscipline(String title) throws ValueAlreadyExistsException {
        DisciplineDTO disciplineDTO = DisciplineDTO.builder()
                .title(title)
                .build();

        return disciplineService.createDiscipline(disciplineDTO);
    }

    private GameMethodDTO insertRandomGameMethod(String title, String type) throws ValueAlreadyExistsException {
        GameMethodDTO methodDTO = GameMethodDTO.builder()
                .title(title)
                .type(type)
                .build();
        return methodService.createGameMethod(methodDTO);
    }

    private TaskDTO insertRandomTask(Long disciplineId) {
        TaskRequest taskDTO = TaskRequest.builder()
                .title("Задача")
                .experience(random.nextInt(1000))
                .answer("Answer")
                .complexity(random.nextInt(10))
                .videoPath("https://devblogs.microsoft.com/python/wp-content/uploads/sites/12/2018/08/pythonfeature.png")
                .description("Python — высокоуровневый язык программирования общего назначения с \" +\n" +
                        "                        \"динамической строгой типизацией и автоматическим управлением памятью, ориентированный \" +\n" +
                        "                        \"на повышение производительности разработчика, читаемости кода и его качества, \" +\n" +
                        "                        \"а также на обеспечение переносимости написанных на нём программ.")
                .discipline(disciplineId)
                .time(Time.valueOf("00:20:00"))
                .build();
        return taskService.createTask(taskDTO);
    }

    private LessonDTO insertRandomLesson(Long disciplineId) {
        LessonRequest lessonDTO = LessonRequest.builder()
                .title("Урок")
                .experience(random.nextInt(1000))
                .complexity(random.nextInt(10))
                .description("Python — высокоуровневый язык программирования общего назначения с " +
                        "динамической строгой типизацией и автоматическим управлением памятью, ориентированный " +
                        "на повышение производительности разработчика, читаемости кода и его качества, " +
                        "а также на обеспечение переносимости написанных на нём программ. Python — высокоуровневый язык программирования общего назначения с \" +\n" +
                        "                        \"динамической строгой типизацией и автоматическим управлением памятью, ориентированный \" +\n" +
                        "                        \"на повышение производительности разработчика, читаемости кода и его качества, \" +\n" +
                        "                        \"а также на обеспечение переносимости написанных на нём программ.Python — высокоуровневый язык программирования общего назначения с \" +\n" +
                        "                        \"динамической строгой типизацией и автоматическим управлением памятью, ориентированный \" +\n" +
                        "                        \"на повышение производительности разработчика, читаемости кода и его качества, \" +\n" +
                        "                        \"а также на обеспечение переносимости написанных на нём программ.")
                .discipline(disciplineId)
                .videoPath("https://www.youtube.com/watch?v=enJvobGuISc")
                .imagePath("https://devblogs.microsoft.com/python/wp-content/uploads/sites/12/2018/08/pythonfeature.png")
                .gameMethods(List.of())
                .tasks(List.of())
                .build();
        return lessonService.createLesson(lessonDTO);
    }

    private NotificationDTO insertRandomNotification(UserDTO sender, List<Long> recipientIds) {
        NotificationDTO notificationDTO = NotificationDTO.builder()
                .title("Привет Бро")
                .details("Приглашаю тебя на мой мегаклассный курс по Питону")
                .senderId(sender.getId())
                .recipientIds(recipientIds)
                .build();
        return notificationService.createNotification(notificationDTO);
    }
}
