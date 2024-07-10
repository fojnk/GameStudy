package ru.nsu.GameStudy.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.models.Tag;
import ru.nsu.GameStudy.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public void createTag(Tag tag) {
        tagRepository.save(tag);
    }

    public void deleteTagById(Integer tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new NotFoundException("task not found"));
        tagRepository.delete(tag);
    }

    public Tag updateTag(Integer tagId, Tag request) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new NotFoundException("task not found"));

        tag.setTitle(request.getTitle());
        return tagRepository.save(tag);
    }

    public Tag getTag(Integer tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new NotFoundException("task not found"));
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Boolean addTagToCourse(Integer courseId, Integer tagId) {
        return false;
    }

    public Boolean deleteTagFromCourse(Integer courseId, Integer tagId) {
        return false;
    }

    public Boolean addTagToGameMethod(Integer gameMethodId, Integer tagId) {
        return false;
    }

    public Boolean deleteTagFromGameMethod(Integer gameMethodId, Integer tagId) {
        return false;
    }
}