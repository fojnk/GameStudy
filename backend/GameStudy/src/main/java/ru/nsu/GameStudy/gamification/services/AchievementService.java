package ru.nsu.GameStudy.gamification.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.gamification.dto.AchievementDTO;
import ru.nsu.GameStudy.gamification.models.Achievement;
import ru.nsu.GameStudy.users.dto.StudentDTO;
import ru.nsu.GameStudy.mappers.AchievementMapper;
import ru.nsu.GameStudy.mappers.StudentMapper;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;
import ru.nsu.GameStudy.gamification.repositories.AchievementRepository;
import ru.nsu.GameStudy.users.models.Student;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    public AchievementDTO createAchievement(AchievementDTO request)
            throws ValueAlreadyExistsException {
        if (achievementRepository.existsByTitle(request.getTitle())) {
            throw new ValueAlreadyExistsException();
        }
        log.info("CREATE achievement {} with name: {}", request.getId(), request.getTitle());
        Achievement ach = Achievement.builder().build();
        achievementMapper.updateAchievementFromDTO(ach, request);

        return save(ach);
    }

    @Transactional
    public void deleteAchievement(Long achievementId) {
        log.info("DELETE achievement with id: {}", achievementId);
        if (achievementRepository.existsById(achievementId)) {
            achievementRepository.delete(getAchievement(achievementId));
        } else {
            log.error("achievement with id: {} not found", achievementId);
            throw new NotFoundException("achievement not found");
        }
    }

    public AchievementDTO updateAchievement(Long id, AchievementDTO request) {
        log.info("UPDATE achievement with id: {}, request: {}", id, request.getTitle());
        var ach = getAchievement(id);

        achievementMapper.updateAchievementFromDTO(ach, request);

        return save(ach);
    }

    public AchievementDTO getAchievementDTO(Long achievementId) {
        return achievementMapper.toDTO(getAchievement(achievementId));
    }

    public List<AchievementDTO> getAllAchievements() {
        log.info("GET all achievements");
        return achievementRepository.findAll().stream().map(achievementMapper::toDTO).toList();
    }

    @Transactional
    public AchievementDTO addAchievementToStudent(Long id, Long studentId) {
        Student student = studentService.getStudent(studentId);
        log.info("ADD achievement {} to student {}", id, studentId);
        Achievement achievement = getAchievement(id);
        achievement.getStudents().add(student);
        return save(achievement);
    }

    @Transactional
    public AchievementDTO removeAchievementFromStudent(Long id, Long studentId) {
        Student student = studentService.getStudent(studentId);
        log.info("REMOVE achievement {} from student {}", id, studentId);
        Achievement achievement = getAchievement(id);
        achievement.getStudents().remove(student);
        return save(achievement);
    }


    @Transactional
    public List<StudentDTO> getStudentsWithAchievement(Long achievementId) {
        log.info("GET students who received achievement {}", achievementId);
        Achievement achievement = getAchievement(achievementId);
        return achievement.getStudents().stream().map(studentMapper::toDTO).toList();
    }

    @Transactional
    public List<AchievementDTO> getAchievementsForStudent(Long studentId) {
        log.info("GET student {} achievements", studentId);
        Student student = studentService.getStudent(studentId);
        return student.getAchievements().stream().map(achievementMapper::toDTO).toList();
    }

    protected AchievementDTO save(Achievement achievement) {
        return achievementMapper.toDTO(achievementRepository.save(achievement));
    }

    protected Achievement getAchievement(Long id) {
        log.info("GET achievement with id: {}", id);
        Optional<Achievement> achievement = achievementRepository.findById(id);
        if (achievement.isEmpty()) {
            log.error("achievement with id: {} not found", id);
            throw new NotFoundException("achievement not found");
        }
        else {
            return achievement.get();
        }
    }
}