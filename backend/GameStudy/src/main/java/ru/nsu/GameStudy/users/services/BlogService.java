package ru.nsu.GameStudy.users.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.users.dto.BlogDTO;
import ru.nsu.GameStudy.mappers.BlogMapper;
import ru.nsu.GameStudy.users.models.Blog;
import ru.nsu.GameStudy.users.repositories.BlogRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogService {
    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;

    public BlogDTO createBlog(BlogDTO request) {
        log.info("CREATE blog with title: {}, description {}", request.getTitle(), request.getText());
        Blog blog = Blog.builder().build();
        blogMapper.updateBlogFromDTO(blog, request);

        return save(blog);
    }

    public void deleteBlog(Long blogId) {
        log.info("DELETE blog with id: {}", blogId);
        if (blogRepository.existsById(blogId)) {
            blogRepository.deleteById(blogId);
        } else {
            log.error("blog with id: {} not found", blogId);
            throw new NotFoundException("blog not found");
        }
    }

    public BlogDTO getBlogDTO(Long id) {
        return blogMapper.toDTO(getBlog(id));
    }

    @Transactional
    public BlogDTO updateBlog(Long blogId, BlogDTO request) {
        log.info("UPDATE blog with id: {}, request: {title: {}, text, {}}"
                , blogId, request.getTitle(), request.getText());
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new NotFoundException("blog not found"));

        blogMapper.updateBlogFromDTO(blog, request);

        return save(blog);
    }

    protected BlogDTO save(Blog blog) {
        return blogMapper.toDTO(blogRepository.save(blog));
    }

    protected Blog getBlog(Long id) {
        log.info("GET blog with id: {}", id);
        Optional<Blog> blog = blogRepository.findById(id);
        if (blog.isEmpty()) {
            log.error("blog with id: {} not found", id);
            throw new NotFoundException("blog not found");
        }
        else {
            return blog.get();
        }
    }
}