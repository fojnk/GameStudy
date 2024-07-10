package ru.nsu.GameStudy.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.dto.DisciplineDTO;
import ru.nsu.GameStudy.models.Discipline;
import ru.nsu.GameStudy.repository.DisciplineRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DisciplineService {
    private final DisciplineRepository disciplineRepository;

    public Discipline getDiscipline(Integer id) {
        return disciplineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("discipline not found"));
    }

    public List<Discipline> getAllDisciplines() {
        return disciplineRepository.findAll();
    }

    public Discipline createDiscipline(DisciplineDTO request) {
        Discipline discipline = Discipline.builder()
                .title(request.getTitle())
                .build();

        return disciplineRepository.save(discipline);
    }

    public void deleteDiscipline(Integer id) {
        var discipline = getDiscipline(id);

        disciplineRepository.delete(discipline);
    }

    public Discipline updateDiscipline(Integer id, DisciplineDTO request) {
        var discipline = getDiscipline(id);

        discipline.setTitle(request.getTitle());

        return disciplineRepository.save(discipline);
    }
}