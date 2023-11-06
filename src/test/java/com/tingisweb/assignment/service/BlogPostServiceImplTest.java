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

/**
 * This test class contains unit tests for the BlogPostServiceImpl class, which is responsible for handling blog-post-related
 * operations.
 */
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

    /**
     * This test case verifies that when attempting to save a Blog Post with a null BlogPostDto, an IllegalArgumentException
     * is thrown. Saving a Blog Post with a null DTO should not be allowed.
     */
    @Test
    void save_Ko_NullDto() {
        // Test that saving a null BlogPostDto results in an IllegalArgumentException.
        assertThrows(
                IllegalArgumentException.class,
                () -> blogPostService.save(null)
        );
    }

    /**
     * This test case verifies that when attempting to save a Blog Post with a null title in the BlogPostDto,
     * an IllegalArgumentException is thrown. Saving a Blog Post with a null title is not allowed.
     */
    @Test
    void save_Ko_NullTitle() {
        // Test that saving a BlogPostDto with a null title results in an IllegalArgumentException.
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setContent("content");

        assertThrows(
                IllegalArgumentException.class,
                () -> blogPostService.save(blogPostDto)
        );
    }

    @Test
    void save_Ko_NullContent() {
        // Create a BlogPostDto with null title and valid content.
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setTitle("title");

        // Test that saving a BlogPostDto with a null title results in an IllegalArgumentException.
        assertThrows(
                IllegalArgumentException.class,
                () -> blogPostService.save(blogPostDto)
        );
    }

    /**
     * This test case verifies that when attempting to save a Blog Post and the authenticated user is not found,
     * an ObjectNotFoundException is thrown. This scenario ensures that the authenticated user exists to associate
     * with the Blog Post.
     */
    @Test
    void save_Ko_UserNotFound() {
        // Create a BlogPostDto with a title and content.
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setTitle("title");
        blogPostDto.setContent("content");

        // Simulate that the authenticated user is not found by throwing an ObjectNotFoundException.
        when(securityService.getAuthenticatedUser())
                .thenThrow((ObjectNotFoundException.class));

        // Test that attempting to save the BlogPostDto results in an ObjectNotFoundException.
        assertThrows(ObjectNotFoundException.class,
                () -> blogPostService.save(blogPostDto));
    }

    /**
     * This test case verifies that saving a valid BlogPostDto results in a successful save operation. It checks that
     * the returned BlogPostDto matches the expected values and that the Blog Post is correctly associated with the
     * authenticated user.
     */
    @Test
    void save_Ok_BlogPostSaved() {
        // Create a BlogPostDto with a title and content.
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setTitle("title");
        blogPostDto.setContent("content");

        // Create a UserEntity to represent the authenticated user.
        UserEntity currentUser = new UserEntity();
        currentUser.setId(1L);
        currentUser.setFirstname("firstname");
        currentUser.setLastname("lastname");

        // Mock the authentication service to return the authenticated user.
        when(securityService.getAuthenticatedUser())
                .thenReturn((currentUser));

        // Prepare the BlogPostDto with author information and publication date.
        blogPostDto.setAuthor(new AuthorDto(currentUser.getId(),
                currentUser.getFirstname()+" "+currentUser.getLastname()));
        blogPostDto.setPublicationDate(LocalDateTime.now());

        // Create a BlogPostEntity representing the Blog Post to be saved.
        BlogPostEntity blogPostEntity = new BlogPostEntity();
        blogPostEntity.setTitle("title");
        blogPostEntity.setContent("content");
        blogPostEntity.setAuthor(currentUser);
        blogPostEntity.setPublicationDate(LocalDateTime.now());

        // Mock the mapper to convert the BlogPostDto to BlogPostEntity.
        when(blogPostMapper.toEntity(blogPostDto)).thenReturn(blogPostEntity);

        // Create a saved BlogPostEntity.
        BlogPostEntity savedBlogPostEntity = new BlogPostEntity();
        savedBlogPostEntity.setId(1L);
        savedBlogPostEntity.setTitle("title");
        savedBlogPostEntity.setContent("content");
        savedBlogPostEntity.setAuthor(currentUser);
        savedBlogPostEntity.setPublicationDate(LocalDateTime.now());

        // Mock the repository to save the BlogPostEntity and return the saved instance.
        when(blogPostRepository.save(blogPostEntity)).thenReturn(savedBlogPostEntity);

        // Create the expected BlogPostDto for the saved Blog Post.
        BlogPostDto savedBlogPostDto = new BlogPostDto();
        savedBlogPostDto.setId(1L);
        savedBlogPostDto.setTitle("title");
        savedBlogPostDto.setContent("content");
        savedBlogPostDto.setAuthor(new AuthorDto(currentUser.getId(),
                currentUser.getFirstname()+" "+currentUser.getLastname()));
        savedBlogPostDto.setPublicationDate(LocalDateTime.now());

        // Mock the mapper to convert the saved BlogPostEntity to BlogPostDto.
        when(blogPostMapper.toDto(savedBlogPostEntity)).thenReturn(savedBlogPostDto);

        // Perform the save operation and verify the result.
        BlogPostDto result = blogPostService.save(blogPostDto);

        // Assertions to validate the result.
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(blogPostDto.getTitle(), result.getTitle());
        assertEquals(blogPostDto.getContent(), result.getContent());
        assertEquals(currentUser.getId(), result.getAuthor().getId());
        assertEquals(currentUser.getFirstname() + " " + currentUser.getLastname(), result.getAuthor().getAuthorName());
        assertNotNull(result.getPublicationDate());

        // Verify that the BlogPostRepository's save method was called.
        Mockito.verify(blogPostRepository).save(blogPostEntity);
    }

    /**
     * This test case verifies that attempting to find a Blog Post by its ID, which does not exist in the DB,
     * results in an ObjectNotFoundException being thrown.
     */
    @Test
    void findById_Ko_BlogPostNotFound() {
        // Define an ID that does not exist in the DB.
        Long id = 9999L;

        // Mock the BlogPostRepository to throw an ObjectNotFoundException when attempting to find by ID.
        when(blogPostRepository.findById(id))
                .thenThrow((ObjectNotFoundException.class));

        // Assert that calling the findById method with the non-existent ID throws an ObjectNotFoundException.
        assertThrows(ObjectNotFoundException.class,
                () -> blogPostService.findById(id));
    }

    /**
     * This test case verifies that a Blog Post can be successfully retrieved by its ID.
     */
    @Test
    void findById_Ok_BlogPostFound() {
        // Create a mock BlogPostEntity with ID, title, and content.
        BlogPostEntity blogPostEntity = new BlogPostEntity();
        blogPostEntity.setId(1L);
        blogPostEntity.setTitle("title");
        blogPostEntity.setContent("content");

        // Define the ID of the Blog Post to retrieve.
        Long id = 1L;

        // Mock the BlogPostRepository to return the BlogPostEntity when findById is called with the defined ID.
        when(blogPostRepository.findById(id))
                .thenReturn(Optional.of(blogPostEntity));

        // Create a corresponding BlogPostDto with the same ID, title, and content.
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setId(1L);
        blogPostDto.setTitle("title");
        blogPostDto.setContent("content");

        // Mock the BlogPostMapper to map the BlogPostEntity to the corresponding BlogPostDto.
        when(blogPostMapper.toDto(blogPostEntity)).thenReturn(blogPostDto);

        // Attempt to retrieve the Blog Post by its ID and verify that it matches the expected BlogPostDto.
        BlogPostDto result = blogPostService.findById(id);

        // Assertions to validate the result.
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    /**
     * This test case verifies that attempting to update a Blog Post without providing an ID results in a MissingIdException.
     */
    @Test
    void update_Ko_MissingId() {
        // Create a BlogPostDto without specifying an ID.
        BlogPostDto blogPostDto = new BlogPostDto();

        // Assert that attempting to update the Blog Post without an ID results in a MissingIdException.
        assertThrows(
                MissingIdException.class,
                () -> blogPostService.update(null,blogPostDto)
        );
    }

    /**
     * This test case verifies that attempting to update a Blog Post with a null BlogPostDto results in an IllegalArgumentException.
     */
    @Test
    void update_Ko_NullDto() {
        // Specify a valid ID but provide a null BlogPostDto for updating.
        Long id = 1L;

        // Assert that attempting to update the Blog Post with a null BlogPostDto results in an IllegalArgumentException.
        assertThrows(
                IllegalArgumentException.class,
                () -> blogPostService.update(id,null)
        );
    }

    /**
     * This test case verifies that attempting to update a Blog Post with a BlogPostDto containing a different ID
     * (i.e., trying to update another entity) results in an EditAnotherEntityException.
     */
    @Test
    void update_Ko_TryingToUpdateAnotherEntity() {
        // Create a BlogPostDto with a different ID than the one specified for updating.
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setId(2L);

        // Specify the ID of the Blog Post to be updated.
        Long id = 1L;

        // Assert that trying to update a different entity results in an EditAnotherEntityException.
        assertThrows(
                EditAnotherEntityException.class,
                () -> blogPostService.update(id,blogPostDto)
        );
    }

    /**
     * This test case verifies that trying to update a Blog Post when the authenticated user is not the author of the Blog Post
     * results in an UnauthorizedException.
     */
    @Test
    void update_Ko_unauthorizedAction() {
        // Create a BlogPostDto for the update operation.
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setId(1L);

        // Specify the ID of the Blog Post to be updated.
        Long id = 1L;

        // Create a BlogPostEntity with the specified ID as the author.
        BlogPostEntity existingEntity = new BlogPostEntity();
        existingEntity.setId(id);
        UserEntity author = new UserEntity();
        author.setId(1L);
        existingEntity.setAuthor(author);

        // Create another UserEntity to simulate a different user trying to update the Blog Post.
        UserEntity anotherAuthor = new UserEntity();
        author.setId(2L);

        // Mock the repository to return the existing BlogPostEntity and the SecurityService to return the different user.
        when(blogPostRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(securityService.getAuthenticatedUser()).thenReturn(anotherAuthor);

        // Assert that trying to update the Blog Post with a different author results in an UnauthorizedException.
        assertThrows(UnauthorizedException.class,
                () -> blogPostService.update(id, blogPostDto));
    }

    /**
     * This test case verifies that trying to update a Blog Post with an ID that does not exist in the repository
     * results in an ObjectNotFoundException.
     */
    @Test
    void update_Ko_BlogPostNotFound() {
        // Create a BlogPostDto for the update operation.
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setId(1L);

        // Specify the ID of the non-existent Blog Post to be updated.
        Long id = 1L;

        // Mock the repository to return an empty optional, simulating the absence of a Blog Post.
        when(blogPostRepository.findById(id)).thenReturn(Optional.empty());

        // Assert that trying to update a non-existent Blog Post results in an ObjectNotFoundException.
        assertThrows(
                ObjectNotFoundException.class,
                () -> blogPostService.update(id,blogPostDto)
        );
    }

    /**
     * This test case verifies the successful updating of a Blog Post and ensures that the result matches the updated BlogPostDto.
     */
    @Test
    void update_Ok_BlogPostUpdated() {
        // Create an updated BlogPostDto with new content.
        BlogPostDto updatedDto = new BlogPostDto();
        updatedDto.setId(1L);

        // Create a UserEntity representing the author of the Blog Post.
        UserEntity author = new UserEntity();
        author.setId(1L);
        updatedDto.setAuthor(new AuthorDto(author.getId(),
                "author name"));
        updatedDto.setTitle("Updated Title");
        updatedDto.setContent("Updated Content");

        // Specify the ID of the existing Blog Post to be updated.
        Long id = 1L;

        // Create an existing BlogPostEntity in the repository.
        BlogPostEntity existingEntity = new BlogPostEntity();
        existingEntity.setId(1L);
        existingEntity.setAuthor(author);
        existingEntity.setTitle("Title");
        existingEntity.setContent("Content");

        // Mock the repository to return the existing BlogPostEntity when the ID is provided.
        when(blogPostRepository.findById(id)).thenReturn(Optional.of(existingEntity));

        // Mock the SecurityService to return the same user for authentication.
        when(securityService.getAuthenticatedUser()).thenReturn(author);

        // Create an updated BlogPostEntity to represent the changes.
        BlogPostEntity updatedEntity = new BlogPostEntity();
        updatedEntity.setId(1L);
        updatedEntity.setAuthor(author);
        updatedEntity.setTitle("Updated Title");
        updatedEntity.setContent("Updated Content");

        // Mock the BlogPostMapper to convert the updatedDto to an updatedEntity.
        when(blogPostMapper.toEntity(updatedDto)).thenReturn(updatedEntity);

        // Mock the repository to return the updatedEntity when saved.
        when(blogPostRepository.save(updatedEntity)).thenReturn(updatedEntity);

        // Mock the BlogPostMapper to convert the updatedEntity to an updatedDto.
        when(blogPostMapper.toDto(updatedEntity)).thenReturn(updatedDto);

        // Call the update method to update the Blog Post.
        BlogPostDto result = blogPostService.update(id, updatedDto);

        // Assertions to validate the result.
        assertNotNull(result);
        assertEquals(existingEntity.getId(), result.getId());
        assertEquals(updatedDto.getTitle(), result.getTitle());
        assertEquals(updatedDto.getContent(), result.getContent());
    }

    /**
     * This test case verifies that attempting to delete a Blog Post with a missing ID results in a MissingIdException.
     */
    @Test
    void delete_Ko_MissingId() {
        // Attempt to delete a Blog Post with a null ID and assert that it throws a MissingIdException.
        assertThrows(
                MissingIdException.class,
                () -> blogPostService.delete(null)
        );
    }

    /**
     * This test case verifies that attempting to delete a Blog Post when the authenticated user is not authorized to do so
     * results in an UnauthorizedException.
     */
    @Test
    void delete_Ko_unauthorizedAction() {
        // Create a BlogPostDto with an ID.
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setId(1L);

        // Set the ID for the target Blog Post.
        Long id = 1L;

        // Create a BlogPostEntity representing the existing Blog Post.
        BlogPostEntity existingEntity = new BlogPostEntity();
        existingEntity.setId(id);

        // Create an author UserEntity.
        UserEntity author = new UserEntity();
        author.setId(1L);

        // Set the author of the existing Blog Post.
        existingEntity.setAuthor(author);

        // Create another UserEntity representing a different user.
        UserEntity anotherAuthor = new UserEntity();
        author.setId(2L);

        // Mock the behavior when trying to find the Blog Post by ID.
        when(blogPostRepository.findById(id)).thenReturn(Optional.of(existingEntity));

        // Mock the behavior to return anotherAuthor as the authenticated user.
        when(securityService.getAuthenticatedUser()).thenReturn(anotherAuthor);

        // Assert that attempting to delete the Blog Post throws an UnauthorizedException.
        assertThrows(UnauthorizedException.class,
                () -> blogPostService.delete(id));
    }

    /**
     * This test case verifies the successful deletion of a Blog Post by its ID. It ensures that when the authenticated user
     * is authorized to delete the Blog Post, it is removed from the repository.
     */
    @Test
    void delete_Ok_blogPostDeleted() {
        // Create a BlogPostDto with an ID.
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setId(1L);

        // Set the ID for the target Blog Post.
        Long id = 1L;

        // Create a BlogPostEntity representing the existing Blog Post.
        BlogPostEntity existingEntity = new BlogPostEntity();
        existingEntity.setId(id);

        // Create an author UserEntity.
        UserEntity author = new UserEntity();
        author.setId(1L);

        // Set the author of the existing Blog Post.
        existingEntity.setAuthor(author);

        // Mock the behavior when trying to find the Blog Post by ID.
        when(blogPostRepository.findById(id)).thenReturn(Optional.of(existingEntity));

        // Mock the behavior to return the same author as the authenticated user.
        when(securityService.getAuthenticatedUser()).thenReturn(author);

        // Call the delete method with the specified ID.
        blogPostService.delete(id);

        // Verify that the delete method resulted in the repository's deleteById method being called with the specified ID.
        verify(blogPostRepository).deleteById(id);
    }

    /**
     * This test case verifies that when attempting to retrieve a page of Blog Posts, and the requested page is empty,
     * the service should throw a NoContentException. An empty page indicates that there are no Blog Posts available
     * for the requested page and size.
     */
    @Test
    void findAll_Ko_EmptyPage() {
        // Set the page and size for the requested page.
        int page = 0;
        int size = 10;

        // Create an empty page of BlogPostEntity instances.
        Page<BlogPostEntity> entitiesEmptyPage = new PageImpl<>(new ArrayList<>());

        // Mock the behavior of the blogPostRepository when trying to find a page of Blog Posts.
        when(blogPostRepository.findAll(any(Pageable.class))).thenReturn(entitiesEmptyPage);

        // Ensure that calling the findAll method with the specified page and size results in a NoContentException.
        assertThrows(NoContentException.class,
                () -> blogPostService.findAll(page,size));
    }

    /**
     * This test case verifies that when attempting to retrieve a page of Blog Posts and the requested page contains
     * Blog Post entities, the service should return a non-empty page of BlogPostDto instances. It checks that the service
     * correctly converts BlogPostEntity objects to BlogPostDto objects and returns a page with the expected number of
     * elements.
     */
    @Test
    void findAll_Ok_getNotEmptyPage() {
        // Set the page and size for the requested page.
        int page = 0;
        int size = 10;

        // Create a sample BlogPostEntity instance.
        BlogPostEntity blogPostEntity = new BlogPostEntity();

        // Create a page containing the sample BlogPostEntity.
        Page<BlogPostEntity> entitiesPage =
                new PageImpl<>(List.of(blogPostEntity));

        // Mock the behavior of the blogPostRepository when trying to find a page of Blog Posts.
        when(blogPostRepository.findAll(any(Pageable.class))).thenReturn(entitiesPage);

        // Create a sample BlogPostDto instance.
        BlogPostDto blogPostDto = new BlogPostDto();

        // Create a page containing the sample BlogPostDto.
        Page<BlogPostDto> dtosPage =
                new PageImpl<>(List.of(blogPostDto));

        // Mock the behavior of the blogPostMapper to convert BlogPostEntity to BlogPostDto.
        when(blogPostMapper.toPageDto(entitiesPage)).thenReturn(dtosPage);

        // Call the service to retrieve the page of BlogPostDto instances.
        Page<BlogPostDto> result = blogPostService.findAll(page,size);

        // Ensure that the result is not null and contains the expected number of elements (1 in this case).
        assertNotNull(result);
        assertEquals(1,result.getNumberOfElements());
    }
}