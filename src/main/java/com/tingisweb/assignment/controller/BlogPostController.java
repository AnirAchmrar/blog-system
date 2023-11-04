package com.tingisweb.assignment.controller;

import com.tingisweb.assignment.dto.BlogPostDto;
import com.tingisweb.assignment.service.BlogPostServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog_posts")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class BlogPostController {

    private final BlogPostServiceImpl blogPostServiceImpl;

    @PostMapping
    public ResponseEntity<BlogPostDto> save(@RequestBody @Valid BlogPostDto blogPost){
        return new ResponseEntity<>(blogPostServiceImpl.save(blogPost), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPostDto> findById(@PathVariable @Valid Long id){
        return new ResponseEntity<>(blogPostServiceImpl.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPostDto> update(@PathVariable @Valid Long id,
                                              @RequestBody @Valid BlogPostDto blogPost){
        return new ResponseEntity<>(blogPostServiceImpl.update(id,blogPost), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Valid Long id){
        blogPostServiceImpl.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<BlogPostDto>> findAll(@RequestParam("page") @Valid Integer page,
                                                     @RequestParam("size") @Valid Integer size){
        return new ResponseEntity<>(blogPostServiceImpl.findAll(page,size),HttpStatus.OK);
    }

    @GetMapping("/my")
    public ResponseEntity<Page<BlogPostDto>> findAllByCurrentUser(@RequestParam("page") @Valid Integer page,
                                                     @RequestParam("size") @Valid Integer size){
        return new ResponseEntity<>(blogPostServiceImpl.findBlogPostsByAuthorId(page,size),HttpStatus.OK);
    }

}
