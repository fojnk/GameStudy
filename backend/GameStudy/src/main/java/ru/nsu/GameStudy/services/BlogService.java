package ru.nsu.GameStudy.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.dto.BlogDTO;
import ru.nsu.GameStudy.models.Blog;
import ru.nsu.GameStudy.repository.BlogRepository;

@Service
@AllArgsConstructor
public class BlogService {
    @Autowired
    private final BlogRepository blogRepository;

    public Blog createBlog(BlogDTO request) {
        Blog blog = Blog.builder()
                .title(request.getTitle())
                .text(request.getText())
                .build();

        return blogRepository.save(blog);
    }

    public void deleteBlog(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new NotFoundException("blog not found"));

        blogRepository.delete(blog);
    }

    public Blog getBlog(Long blogId) {
        return blogRepository.findById(blogId)
                .orElseThrow(() -> new NotFoundException("blog not found"));
    }

    public Blog updateBlog(Long blogId, BlogDTO request) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new NotFoundException("blog not found"));

        blog.setTitle(request.getTitle());
        blog.setText(request.getText());

        return blogRepository.save(blog);
    }
}