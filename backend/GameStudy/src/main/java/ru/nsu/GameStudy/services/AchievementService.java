package ru.nsu.GameStudy.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.dto.AchievementDTO;
import ru.nsu.GameStudy.models.Achievements;
import ru.nsu.GameStudy.repository.AchievementRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    public Achievements createAchievement(AchievementDTO request) {
        Achievements ach = Achievements.builder()
                .id(request.getId())
                .title(request.getTitle())
                .requiredExp(request.getRequiredExp())
                .build();

        return achievementRepository.save(ach);
    }

    public void deleteAchievement(Long achievementId) {
        achievementRepository.delete(getAchievement(achievementId));
    }

    public void updateAchievement(Long id, AchievementDTO request) {
        var ach = getAchievement(id);

        ach.setTitle(request.getTitle());

        achievementRepository.save(ach);
    }

    public Achievements getAchievement(Long achievementId) {

        return achievementRepository.findById(achievementId)
                .orElseThrow(() -> new NotFoundException("achievement not found"));
    }

    public List<Achievements> getAllAchievements() {
        return achievementRepository.findAll();
    }
}