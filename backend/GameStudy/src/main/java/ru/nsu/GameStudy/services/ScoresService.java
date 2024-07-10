package ru.nsu.GameStudy.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.GameStudy.models.Scores;
import ru.nsu.GameStudy.repository.ScoresRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScoresService {
    @Autowired
    private ScoresRepository scoresRepository;

    public Optional<List<Scores>> getScoresByStudentId(Long studentId) {
        return scoresRepository.findByStudent_id(studentId);
    }

    public Optional<List<Scores>> getScoresByCourseId(Long courseId) {
        return scoresRepository.findByStudent_id(courseId);
    }
}
