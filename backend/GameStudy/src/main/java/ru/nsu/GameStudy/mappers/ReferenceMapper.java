package ru.nsu.GameStudy.mappers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.GameStudy.authentication.models.Notification;
import ru.nsu.GameStudy.authentication.models.User;
import ru.nsu.GameStudy.course_attributes.models.CourseTag;
import ru.nsu.GameStudy.course_attributes.models.GameTag;
import ru.nsu.GameStudy.gamification.models.Score;
import ru.nsu.GameStudy.gamification.models.ScoresBoard;
import ru.nsu.GameStudy.courses.models.Course;
import ru.nsu.GameStudy.course_attributes.models.Discipline;
import ru.nsu.GameStudy.courses.models.Lesson;
import ru.nsu.GameStudy.courses.models.Task;
import ru.nsu.GameStudy.gamification.models.Achievement;
import ru.nsu.GameStudy.gamification.models.GameMethod;
import ru.nsu.GameStudy.student_activity.models.TaskActivity;
import ru.nsu.GameStudy.users.models.Blog;
import ru.nsu.GameStudy.users.models.Student;
import ru.nsu.GameStudy.users.models.StudentGroup;
import ru.nsu.GameStudy.users.models.Teacher;

@Component
@RequiredArgsConstructor
public class ReferenceMapper {
    @PersistenceContext
    private final EntityManager entityManager;

    public Long blogToId(Blog blog) {
        return blog.getId();
    }

    protected Blog idToBlog(Long id) {
        return entityManager.find(Blog.class, id);
    }

    public Long courseTagToId(CourseTag tag) {
        return tag.getId();
    }

    protected CourseTag idToCourseTag(Long id) {
        return entityManager.find(CourseTag.class, id);
    }

    public Long gameTagToId(GameTag tag) {
        return tag.getId();
    }

    protected GameTag idToGameTag(Long id) {
        return entityManager.find(GameTag.class, id);
    }

    public Long courseToId(Course course) {
        return course.getId();
    }

    protected Course idToCourse(Long id) {
        return entityManager.find(Course.class, id);
    }

    public Long teacherToId(Teacher teacher) {
        return teacher.getId();
    }

    protected Teacher idToTeacher(Long id) {
        return entityManager.find(Teacher.class, id);
    }

    public Long lessonToId(Lesson lesson) {
        return lesson.getId();
    }

    protected Lesson idToLesson(Long id) {
        return entityManager.find(Lesson.class, id);
    }

    public Long studentToId(Student student) {
        return student.getId();
    }

    protected Student idToStudent(Long id) {
        return entityManager.find(Student.class, id);
    }

    public Long achievementToId(Achievement achievement) {
        return achievement.getId();
    }

    protected Achievement idToAchievement(Long id) {
        return entityManager.find(Achievement.class, id);
    }

    public Long disciplineToId(Discipline discipline) {
        return discipline.getId();
    }

    protected Discipline idToDiscipline(Long id) {
        return entityManager.find(Discipline.class, id);
    }

    public Long taskToId(Task task) {
        return task.getId();
    }

    protected Task idToTask(Long id) {
        return entityManager.find(Task.class, id);
    }

    public Long taskResultToId(TaskActivity taskActivity) {
        return taskActivity.getId();
    }

    protected TaskActivity idToTaskResult(Long id) {
        return entityManager.find(TaskActivity.class, id);
    }

    public Long boardResultToId(ScoresBoard scoresBoard) {
        return scoresBoard.getId();
    }

    protected ScoresBoard idToScoresBoard(Long id) {
        return entityManager.find(ScoresBoard.class, id);
    }

    public Long scoreResultToId(Score score) {
        return score.getId();
    }

    protected Score idToScore(Long id) {
        return entityManager.find(Score.class, id);
    }

    public Long userToId(User user) {
        return user.getId();
    }

    protected User idToUser(Long id) {
        return entityManager.find(User.class, id);
    }

    public Long gameMethodToId(GameMethod gameMethod) {
        return gameMethod.getId();
    }

    protected GameMethod idToGameMethod(Long id) {
        return entityManager.find(GameMethod.class, id);
    }

    public Long groupToId(StudentGroup group) {
        return group.getId();
    }

    protected StudentGroup idToStudentGroup(Long id) {
        return entityManager.find(StudentGroup.class, id);
    }

    public Long notificationToId(Notification notification) {
        return notification.getId();
    }

    protected Notification idToNotification(Long id) {
        return entityManager.find(Notification.class, id);
    }
}
