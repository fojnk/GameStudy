package ru.nsu.GameStudy.course_attributes.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.course_attributes.dto.DisciplineDTO;
import ru.nsu.GameStudy.courses.dto.CourseDTO;
import ru.nsu.GameStudy.mappers.DisciplineMapper;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;
import ru.nsu.GameStudy.course_attributes.models.Discipline;
import ru.nsu.GameStudy.course_attributes.repositories.DisciplineRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DisciplineService {
    private final DisciplineRepository disciplineRepository;
    private final DisciplineMapper disciplineMapper;

    public DisciplineDTO getDisciplineDTO(Long id) {
        return disciplineMapper.toDTO(getDiscipline(id));
    }

    public List<DisciplineDTO> getAllDisciplines() {
        log.info("GET all disciplines");
        return disciplineRepository.findAll().stream().map(disciplineMapper::toDTO).toList();
    }

    public DisciplineDTO createDiscipline(DisciplineDTO request) throws ValueAlreadyExistsException {
        if (disciplineRepository.existsByTitle(request.getTitle())) {
            throw new ValueAlreadyExistsException();
        }
        log.info("CREATE discipline with title: {}", request.getTitle());
        Discipline discipline = Discipline.builder().build();
        disciplineMapper.updateDisciplineFromDTO(discipline, request);

        return save(discipline);
    }

    public void deleteDiscipline(Long id) {
        log.info("DELETE discipline with id: {}", id);
        if (disciplineRepository.existsById(id)) {
            disciplineRepository.deleteById(id);
        } else {
            throw new NotFoundException("discipline not found");
        }
    }

    public DisciplineDTO updateDiscipline(Long id, DisciplineDTO request) {
        log.info("UPDATE discipline with id: {}, request: {title: {}}", id, request.getTitle());
        var discipline = getDiscipline(id);

        disciplineMapper.updateDisciplineFromDTO(discipline, request);

        return save(discipline);
    }

    protected DisciplineDTO save(Discipline discipline) {
        return disciplineMapper.toDTO(disciplineRepository.save(discipline));
    }

    protected Discipline getDiscipline(Long id) {
        log.info("GET discipline with id: {}", id);
        Optional<Discipline> discipline = disciplineRepository.findById(id);
        if (discipline.isEmpty()) {
            log.error("discipline with id: {} not found", id);
            throw new NotFoundException("discipline not found");
        }
        else {
            return discipline.get();
        }
    }
}
