package com.tingisweb.assignment.service;

import com.tingisweb.assignment.dto.AuthorDto;
import com.tingisweb.assignment.dto.BlogPostDto;
import com.tingisweb.assignment.entity.BlogPostEntity;
import com.tingisweb.assignment.entity.UserEntity;
import com.tingisweb.assignment.errorhandling.exception.*;
import com.tingisweb.assignment.mapper.BlogPostMapper;
import com.tingisweb.assignment.repository.BlogPostRepository;
import com.tingisweb.assignment.security.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void update_Ko_MissingId() {
        BlogPostDto blogPostDto = new BlogPostDto();

        assertThrows(
                MissingIdException.class,
                () -> blogPostService.update(null,blogPostDto)
        );
    }

    @Test
    void update_Ko_NullDto() {
        Long id = 1L;

        assertThrows(
                IllegalArgumentException.class,
                () -> blogPostService.update(id,null)
        );
    }

    @Test
    void update_Ko_TryingToUpdateAnotherEntity() {
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setId(2L);
        Long id = 1L;

        assertThrows(
                EditAnotherEntityException.class,
                () -> blogPostService.update(id,blogPostDto)
        );
    }

    @Test
    void update_Ko_unauthorizedAction() {
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setId(1L);
        Long id = 1L;

        BlogPostEntity existingEntity = new BlogPostEntity();
        existingEntity.setId(id);
        UserEntity author = new UserEntity();
        author.setId(1L);
        existingEntity.setAuthor(author);

        UserEntity anotherAuthor = new UserEntity();
        author.setId(2L);

        when(blogPostRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(securityService.getAuthenticatedUser()).thenReturn(anotherAuthor);
        assertThrows(UnauthorizedException.class,
                () -> blogPostService.update(id, blogPostDto));
    }

    @Test
    void update_Ko_BlogPostNotFound() {
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setId(1L);
        Long id = 1L;

        when(blogPostRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                ObjectNotFoundException.class,
                () -> blogPostService.update(id,blogPostDto)
        );
    }

    @Test
    void update_Ok_BlogPostUpdated() {
        BlogPostDto updatedDto = new BlogPostDto();
        updatedDto.setId(1L);
        UserEntity author = new UserEntity();
        author.setId(1L);
        updatedDto.setAuthor(new AuthorDto(author.getId(),
                "author name"));
        updatedDto.setTitle("Updated Title");
        updatedDto.setContent("Updated Content");

        Long id = 1L;

        BlogPostEntity existingEntity = new BlogPostEntity();
        existingEntity.setId(1L);
        existingEntity.setAuthor(author);
        existingEntity.setTitle("Title");
        existingEntity.setContent("Content");

        when(blogPostRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(securityService.getAuthenticatedUser()).thenReturn(author);

        BlogPostEntity updatedEntity = new BlogPostEntity();
        updatedEntity.setId(1L);
        updatedEntity.setAuthor(author);
        updatedEntity.setTitle("Updated Title");
        updatedEntity.setContent("Updated Content");

        when(blogPostMapper.toEntity(updatedDto)).thenReturn(updatedEntity);
        when(blogPostRepository.save(updatedEntity)).thenReturn(updatedEntity);
        when(blogPostMapper.toDto(updatedEntity)).thenReturn(updatedDto);

        BlogPostDto result = blogPostService.update(id, updatedDto);

        assertNotNull(result);
        assertEquals(existingEntity.getId(), result.getId());
        assertEquals(updatedDto.getTitle(), result.getTitle());
        assertEquals(updatedDto.getContent(), result.getContent());
    }

    @Test
    void delete_Ko_MissingId() {
        assertThrows(
                MissingIdException.class,
                () -> blogPostService.delete(null)
        );
    }

    @Test
    void delete_Ko_unauthorizedAction() {
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setId(1L);
        Long id = 1L;

        BlogPostEntity existingEntity = new BlogPostEntity();
        existingEntity.setId(id);
        UserEntity author = new UserEntity();
        author.setId(1L);
        existingEntity.setAuthor(author);

        UserEntity anotherAuthor = new UserEntity();
        author.setId(2L);

        when(blogPostRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(securityService.getAuthenticatedUser()).thenReturn(anotherAuthor);
        assertThrows(UnauthorizedException.class,
                () -> blogPostService.delete(id));
    }

    @Test
    void delete_Ok_blogPostDeleted() {
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setId(1L);
        Long id = 1L;

        BlogPostEntity existingEntity = new BlogPostEntity();
        existingEntity.setId(id);
        UserEntity author = new UserEntity();
        author.setId(1L);
        existingEntity.setAuthor(author);

        when(blogPostRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(securityService.getAuthenticatedUser()).thenReturn(author);

        blogPostService.delete(id);

        verify(blogPostRepository).deleteById(id);
    }

    @Test
    void findAll_Ko_EmptyPage() {
        int page = 0;
        int size = 10;

        Page<BlogPostEntity> entitiesEmptyPage = new PageImpl<>(new ArrayList<>());

        when(blogPostRepository.findAll(any(Pageable.class))).thenReturn(entitiesEmptyPage);

        assertThrows(NoContentException.class,
                () -> blogPostService.findAll(page,size));
    }

    @Test
    void findAll_Ok_getNotEmptyPage() {
        int page = 0;
        int size = 10;

        BlogPostEntity blogPostEntity = new BlogPostEntity();

        Page<BlogPostEntity> entitiesPage =
                new PageImpl<>(List.of(blogPostEntity));

        when(blogPostRepository.findAll(any(Pageable.class))).thenReturn(entitiesPage);

        BlogPostDto blogPostDto = new BlogPostDto();

        Page<BlogPostDto> dtosPage =
                new PageImpl<>(List.of(blogPostDto));

        when(blogPostMapper.toPageDto(entitiesPage)).thenReturn(dtosPage);

        Page<BlogPostDto> result = blogPostService.findAll(page,size);

        assertNotNull(result);
        assertEquals(1,result.getNumberOfElements());
    }
}