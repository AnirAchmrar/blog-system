package com.tingisweb.assignment.service;

import com.tingisweb.assignment.dto.AuthorDto;
import com.tingisweb.assignment.dto.BlogPostDto;
import com.tingisweb.assignment.entity.BlogPostEntity;
import com.tingisweb.assignment.entity.UserEntity;
import com.tingisweb.assignment.errorhandling.exception.ObjectNotFoundException;
import com.tingisweb.assignment.mapper.BlogPostMapper;
import com.tingisweb.assignment.repository.BlogPostRepository;
import com.tingisweb.assignment.security.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BlogPostServiceImplTest {

    @InjectMocks
    BlogPostServiceImpl blogPostService;

    @Mock
    private BlogPostMapper blogPostMapper;
    @Mock
    private BlogPostRepository blogPostRepository;
    @Mock
    private SecurityService securityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_Ko_NullDto() {
        assertThrows(
                IllegalArgumentException.class,
                () -> blogPostService.save(null)
        );
    }

    @Test
    void save_Ko_NullTitle() {
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setContent("content");

        assertThrows(
                IllegalArgumentException.class,
                () -> blogPostService.save(blogPostDto)
        );
    }

    @Test
    void save_Ko_NullContent() {
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setTitle("title");

        assertThrows(
                IllegalArgumentException.class,
                () -> blogPostService.save(blogPostDto)
        );
    }

    @Test
    void save_Ko_UserNotFound() {
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setTitle("title");
        blogPostDto.setContent("content");

        when(securityService.getAuthenticatedUser())
                .thenThrow((ObjectNotFoundException.class));

        assertThrows(ObjectNotFoundException.class,
                () -> blogPostService.save(blogPostDto));
    }

    @Test
    void save_Ok_BlogPostSaved() {
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setTitle("title");
        blogPostDto.setContent("content");

        UserEntity currentUser = new UserEntity();
        currentUser.setId(1L);
        currentUser.setFirstname("firstname");
        currentUser.setLastname("lastname");

        when(securityService.getAuthenticatedUser())
                .thenReturn((currentUser));

        blogPostDto.setAuthor(new AuthorDto(currentUser.getId(),
                currentUser.getFirstname()+" "+currentUser.getLastname()));
        blogPostDto.setPublicationDate(LocalDateTime.now());

        BlogPostEntity blogPostEntity = new BlogPostEntity();
        blogPostEntity.setTitle("title");
        blogPostEntity.setContent("content");
        blogPostEntity.setAuthor(currentUser);
        blogPostEntity.setPublicationDate(LocalDateTime.now());

        when(blogPostMapper.toEntity(blogPostDto)).thenReturn(blogPostEntity);

        BlogPostEntity savedBlogPostEntity = new BlogPostEntity();
        savedBlogPostEntity.setId(1L);
        savedBlogPostEntity.setTitle("title");
        savedBlogPostEntity.setContent("content");
        savedBlogPostEntity.setAuthor(currentUser);
        savedBlogPostEntity.setPublicationDate(LocalDateTime.now());

        when(blogPostRepository.save(blogPostEntity)).thenReturn(savedBlogPostEntity);

        BlogPostDto savedBlogPostDto = new BlogPostDto();
        savedBlogPostDto.setId(1L);
        savedBlogPostDto.setTitle("title");
        savedBlogPostDto.setContent("content");
        savedBlogPostDto.setAuthor(new AuthorDto(currentUser.getId(),
                currentUser.getFirstname()+" "+currentUser.getLastname()));
        savedBlogPostDto.setPublicationDate(LocalDateTime.now());


        when(blogPostMapper.toDto(savedBlogPostEntity)).thenReturn(savedBlogPostDto);

        BlogPostDto result = blogPostService.save(blogPostDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(blogPostDto.getTitle(), result.getTitle());
        assertEquals(blogPostDto.getContent(), result.getContent());
        assertEquals(currentUser.getId(), result.getAuthor().getId());
        assertEquals(currentUser.getFirstname() + " " + currentUser.getLastname(), result.getAuthor().getAuthorName());
        assertNotNull(result.getPublicationDate());

        Mockito.verify(blogPostRepository).save(blogPostEntity);
    }

    @Test
    void findById_Ko_BlogPostNotFound() {
        Long id = 9999L;

        when(blogPostRepository.findById(id))
                .thenThrow((ObjectNotFoundException.class));

        assertThrows(ObjectNotFoundException.class,
                () -> blogPostService.findById(id));
    }

    @Test
    void findById_Ok_BlogPostFound() {
        BlogPostEntity blogPostEntity = new BlogPostEntity();
        blogPostEntity.setId(1L);
        blogPostEntity.setTitle("title");
        blogPostEntity.setContent("content");

        Long id = 1L;

        when(blogPostRepository.findById(id))
                .thenReturn(Optional.of(blogPostEntity));

        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setId(1L);
        blogPostDto.setTitle("title");
        blogPostDto.setContent("content");

        when(blogPostMapper.toDto(blogPostEntity)).thenReturn(blogPostDto);

        BlogPostDto result = blogPostService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findAll() {
    }
}