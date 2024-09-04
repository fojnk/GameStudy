package ru.nsu.GameStudy.gamification.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.gamification.dto.ScoreDTO;
import ru.nsu.GameStudy.users.dto.StudentDTO;
import ru.nsu.GameStudy.mappers.ScoreMapper;
import ru.nsu.GameStudy.mappers.StudentMapper;
import ru.nsu.GameStudy.gamification.models.Score;
import ru.nsu.GameStudy.gamification.repositories.ScoreRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final ScoreMapper scoreMapper;
    private final StudentMapper studentMapper;

    @Transactional
    public ScoreDTO createScore(ScoreDTO request) {
        log.info("CREATE score request: {student: {}, " +
                        "time: {}, experience: {}}",
                request.getStudentId(),
                request.getTime(), request.getExperience());

        Score score = Score.builder().build();
        scoreMapper.updateScoreFromDTO(score, request);

        return save(score);
    }

    public List<ScoreDTO> getAllScores() {
        log.info("GET all scores");
        return scoreRepository.findAll().stream().map(scoreMapper::toDTO).toList();
    }

    public ScoreDTO getScoreDTO(Long scoreId) {
        return scoreMapper.toDTO(getScore(scoreId));
    }

    @Transactional
    public void deleteScoreById(Long scoreId) {
        log.info("DELETE score with id: {}", scoreId);
        if (scoreRepository.existsById(scoreId)) {
            scoreRepository.deleteById(scoreId);
        } else {
            throw new NotFoundException("score not found");
        }
    }

    @Transactional
    public ScoreDTO updateScore(Long scoreId, ScoreDTO request) {
        log.info("UPDATE score with id: {}", scoreId);
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new NotFoundException("score not found"));

        scoreMapper.updateScoreFromDTO(score, request);

        return save(score);
    }

    @Transactional
    public StudentDTO getStudentInScore(Long id) {
        log.info("GET student in score {}", id);
        Score score = getScore(id);
        return studentMapper.toDTO(score.getStudent());
    }

    protected ScoreDTO save(Score score) {
        return scoreMapper.toDTO(scoreRepository.save(score));
    }

    protected Score getScore(Long id) {
        log.info("GET score with id: {}", id);
        Optional<Score> score = scoreRepository.findById(id);
        if (score.isEmpty()) {
            log.error("score with id: {} not found", id);
            throw new NotFoundException("score not found");
        }
        else {
            return score.get();
        }
    }
}
