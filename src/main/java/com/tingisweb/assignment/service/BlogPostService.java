package com.tingisweb.assignment.service;

import com.tingisweb.assignment.dto.BlogPostDto;
import com.tingisweb.assignment.errorhandling.exception.*;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * An interface for managing blog post-related operations.
 */
public interface BlogPostService {

    /**
     * Save a new blog post.
     *
     * @param dto The DTO representing the blog post to be saved.
     * @return The DTO of the saved blog post.
     * @throws IllegalArgumentException If the provided data is invalid or missing.
     */
    BlogPostDto save(BlogPostDto dto) throws IllegalArgumentException;

    /**
     * Find a blog post by its ID.
     *
     * @param id The ID of the blog post to find.
     * @return The DTO of the found blog post.
     * @throws ObjectNotFoundException If the specified blog post is not found.
     */
    BlogPostDto findById(Long id) throws ObjectNotFoundException;


    /**
     * Update an existing blog post by its ID.
     *
     * @param id The ID of the blog post to update.
     * @param dto The DTO containing the updated blog post data.
     * @return The DTO of the updated blog post.
     * @throws MissingIdException If the provided ID is missing or null.
     * @throws IllegalArgumentException If the provided data is invalid or missing.
     * @throws EditAnotherEntityException If the provided ID doesn't match the ID in the DTO.
     * @throws ObjectNotFoundException If the specified blog post is not found.
     * @throws UnauthorizedException If the authenticated user is not authorized for the action.
     */
    BlogPostDto update(Long id, BlogPostDto dto) throws MissingIdException, IllegalArgumentException,
            EditAnotherEntityException, ObjectNotFoundException, UnauthorizedException;

    /**
     * Delete a blog post by its ID.
     *
     * @param id The ID of the blog post to delete.
     * @throws ObjectNotFoundException If the specified blog post is not found.
     * @throws UnauthorizedException If the authenticated user is not authorized for the action.
     */
    void delete(Long id) throws MissingIdException, ObjectNotFoundException, UnauthorizedException;

    /**
     * Find all blog posts with pagination.
     *
     * @param page The page number.
     * @param size The number of blog posts per page.
     * @return A page of blog post DTOs.
     * @throws NoContentException If no blog posts are found.
     */
    Page<BlogPostDto> findAll(Integer page, Integer size) throws NoContentException;

    /**
     * Find all blog posts written by a specific author with pagination.
     *
     * @param page The page number.
     * @param size The number of blog posts per page.
     * @return A page of blog post DTOs written by the specified author.
     * @throws UsernameNotFoundException If the author is not found.
     * @throws NoContentException If no blog posts are found.
     */
    Page<BlogPostDto> findBlogPostsByAuthorId(Integer page, Integer size) throws
            UsernameNotFoundException, NoContentException;

    /**
     * Checks if the currently authenticated user is authorized to perform an action on a blog post
     *
     * @param blogPostId blogPostId The ID of the blog post on which the action is to be performed.
     * @throws ObjectNotFoundException If the specified blog post is not found.
     * @throws UnauthorizedException If the currently authenticated user is not authorized to perform the action.
     */
    void currentUserAuthorizedForAction(Long blogPostId) throws ObjectNotFoundException,
            UnauthorizedException;

}
