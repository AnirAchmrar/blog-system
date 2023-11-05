package com.tingisweb.assignment.controller;

import com.tingisweb.assignment.dto.BlogPostDto;
import com.tingisweb.assignment.service.BlogPostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This is a REST controller that handle blog post related requests (Creation, Retrieve,
 * Update and Delete). It is exposed with the endpoint 'blog_posts'.
 * This endpoint access is permitted to authenticated clients only.
 */
@RestController
@RequestMapping("/blog_posts")
@AllArgsConstructor
public class BlogPostController {

    private final BlogPostService blogPostService;

    /**
     * This endpoint is called by a POST request to create a blog post by posting the blog post object.
     *
     * @param blogPost the blog post object that wrap the necessary data for blog post creation,
     *                 post title and content.
     * @return for a successful creation the endpoint responds with the created blog post with all
     * its data (like ID, author name...) with an OK http status. For any data validation error, the
     * WebExceptionHandler will handle the request and response with the appropriate error message
     * and a BAD_REQUEST http status.
     */
    @PostMapping
    public ResponseEntity<BlogPostDto> save(@RequestBody @Valid BlogPostDto blogPost){
        return new ResponseEntity<>(blogPostService.save(blogPost), HttpStatus.OK);
    }

    /**
     * This endpoint is called by a GET request to retrieve a blog post by the ID that is present in
     * the path.
     *
     * @param id the blog post ID.
     * @return for a successful retrieve, if the given ID is actually has a mapping to and existing
     * blog post, the endpoint responds with the blog post object with all its data. Otherwise, if no
     * mapping was found for the given ID, the WebExceptionHandler responds with a message error of
     * 'Blog post not found!' and a BAD_REQUEST http status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BlogPostDto> findById(@PathVariable @Valid Long id){
        return new ResponseEntity<>(blogPostService.findById(id), HttpStatus.OK);
    }

    /**
     * This endpoint is called by a PUT request to update a blog post by providing the ID of the
     * update intended blog post and the blog post object with updated data.
     *
     * @param id the blog post ID to update.
     * @param blogPost the blog post object with updated data.
     * @return for a successful update, the endpoint responds with the updated blog post. Otherwise,
     * the update operation may cause MissingIdException, IllegalArgumentException,
     * EditAnotherEntityException or ObjectNotFoundException. For all those exceptions, the
     * WebExceptionHandler responds with the appropriate error message and a BAD_REQUEST http status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BlogPostDto> update(@PathVariable @Valid Long id,
                                              @RequestBody @Valid BlogPostDto blogPost){
        return new ResponseEntity<>(blogPostService.update(id,blogPost), HttpStatus.OK);
    }

    /**
     * This endpoint is called by a DELETE request to delete a blog post by providing the if of the
     * deletion intended blog.
     *
     * @param id the blog post ID to delete.
     * @return for a successful deletion of the blog post, the endpoint responds with an empty
     * response body and ah OK http status. Otherwise, the deletion operation may cause
     * MissingIdException and ObjectNotFoundException, which trigger the response of WebExceptionHandler
     * with the appropriate message error and a BAD_REQUEST http status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Valid Long id){
        blogPostService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This endpoint is called by a GET request to retrieve the page of blog posts by providing the
     * wanted page number and its size.
     *
     * @param page the page number.
     * @param size the size of the page.
     * @return for a successful retrieve of blog posts, the endpoint responds with page object that
     * contain page data (page number, total elements...) and the content (blog posts). Otherwise, if
     * no content was found for the given page number a NoContentException will occur, which will
     * trigger the WebExceptionHandler response with the error message 'No content found!' and a
     * BAD_REQUEST http status.
     */
    @GetMapping
    public ResponseEntity<Page<BlogPostDto>> findAll(@RequestParam("page") @Valid Integer page,
                                                     @RequestParam("size") @Valid Integer size){
        return new ResponseEntity<>(blogPostService.findAll(page,size),HttpStatus.OK);
    }

    /**
     * This endpoint is called by a GET request to retrieve the page of blog posts corresponding to
     * the authenticated user that sends the request by providing the wanted page number and its size.
     *
     * @param page the page number.
     * @param size the size of the page.
     * @return for a successful retrieve of blog posts, the endpoint responds with page object that
     * contain page data (page number, total elements...) and the content (blog posts). Otherwise, if
     * no content was found for the given page number and authenticated user a NoContentException
     * will occur, which will trigger the WebExceptionHandler response with the error message 'No
     * content found!' and a BAD_REQUEST http status. An UsernameNotFoundException may happen if the
     * token was corrupted somehow after the request passes the security filter which will trigger
     * WebExceptionHandler to responds with 'Unauthenticated' error message and UNAUTHORIZED http
     * status.
     */
    @GetMapping("/my")
    public ResponseEntity<Page<BlogPostDto>> findAllByCurrentUser(@RequestParam("page") @Valid Integer page,
                                                     @RequestParam("size") @Valid Integer size){
        return new ResponseEntity<>(blogPostService.findBlogPostsByAuthorId(page,size),HttpStatus.OK);
    }

}
