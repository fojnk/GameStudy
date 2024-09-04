package ru.nsu.GameStudy.gamification.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.gamification.dto.ScoreDTO;
import ru.nsu.GameStudy.gamification.dto.ScoresBoardDTO;
import ru.nsu.GameStudy.mappers.ScoreMapper;
import ru.nsu.GameStudy.mappers.ScoresBoardMapper;
import ru.nsu.GameStudy.courses.models.Course;
import ru.nsu.GameStudy.gamification.models.ScoresBoard;
import ru.nsu.GameStudy.gamification.repositories.ScoresBoardRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScoresBoardService {
    private final ScoresBoardRepository boardRepository;
    private final ScoresBoardMapper boardMapper;
    private final ScoreService scoreService;
    private final ScoreMapper scoreMapper;
    private final CourseService courseService;

    @Transactional
    public ScoresBoardDTO createScoresBoard(ScoresBoardDTO request) {
        log.info("CREATE score board request: {title: {}}",
                request.getTitle());
        ScoresBoard board = ScoresBoard.builder().build();
        boardMapper.updateBoardFromDTO(board, request);

        return save(board);
    }

    public List<ScoresBoardDTO> getAllBoards() {
        log.info("GET all scores boards");
        return boardRepository.findAll().stream().map(boardMapper::toDTO).toList();
    }

    public ScoresBoardDTO getScoresBoardDTO(Long boardId) {
        return boardMapper.toDTO(getScoresBoard(boardId));
    }

    @Transactional
    public void deleteBoardById(Long boardId) {
        log.info("DELETE score board with id: {}", boardId);
        if (boardRepository.existsById(boardId)) {
            boardRepository.deleteById(boardId);
        } else {
            throw new NotFoundException("scores board not found");
        }
    }

    @Transactional
    public ScoresBoardDTO updateScoresBoard(Long boardId, ScoresBoardDTO request) {
        log.info("UPDATE score board with id: {}", boardId);
        ScoresBoard board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("scores board not found"));

        boardMapper.updateBoardFromDTO(board, request);

        return save(board);
    }

    @Transactional
    public List<ScoreDTO> getScoresInBoard(Long boardId) {
        log.info("GET scores from scores board {}", boardId);
        ScoresBoard board = getScoresBoard(boardId);
        return board.getScores().stream().map(scoreMapper::toDTO).toList();
    }

    @Transactional
    public ScoresBoardDTO addScoreToBoard(Long boardId, ScoreDTO request) {
        log.info("ADD score {} to scores board {}", request.getId(), boardId);
        ScoresBoard board = getScoresBoard(boardId);
        ScoreDTO score = scoreService.createScore(request);
        board.getScores().add(scoreService.getScore(score.getId()));
        return save(board);
    }

    @Transactional
    public ScoresBoardDTO removeScoreFromBoard(Long boardId, Long scoreId) {
        log.info("REMOVE score {} from scores board {}", scoreId, boardId);
        ScoresBoard board = getScoresBoard(boardId);
        board.getScores().remove(scoreService.getScore(scoreId));
        return save(board);
    }

    @Transactional
    public List<ScoresBoardDTO> getScoresBoardsInCourse(Long courseId) {
        log.info("GET course: {} scores boards", courseId);
        Course course = courseService.getCourse(courseId);
        return course.getBoards().stream().map(boardMapper::toDTO).toList();
    }

    @Transactional
    public ScoresBoardDTO addScoresBoardToCourse(Long boardId, Long courseId) {
        log.info("ADD scores board {} to course: {}", boardId, courseId);
        ScoresBoard board = getScoresBoard(boardId);
        board.setCourse(courseService.getCourse(courseId));
        return save(board);
    }

    @Transactional
    public ScoresBoardDTO removeScoresBoardFromCourse(Long boardId, Long courseId) {
        log.info("REMOVE scores board {} from course: {}", boardId, courseId);
        ScoresBoard board = getScoresBoard(boardId);
        board.setCourse(null);
        return save(board);
    }

    public List<ScoresBoardDTO> getScoresBoardsByStudent(Long studentId) {
        log.info("GET scores boards for student {}", studentId);
        return boardRepository
                .findByCourse_Students_id(studentId)
                .stream()
                .map(boardMapper::toDTO)
                .toList();
    }

    protected ScoresBoardDTO save(ScoresBoard board) {
        return boardMapper.toDTO(boardRepository.save(board));
    }

    protected ScoresBoard getScoresBoard(Long id) {
        log.info("GET score board with id: {}", id);
        Optional<ScoresBoard> board = boardRepository.findById(id);
        if (board.isEmpty()) {
            log.error("scores board with id: {} not found", id);
            throw new NotFoundException("scoresBoard not found");
        }
        else {
            return board.get();
        }
    }
}
