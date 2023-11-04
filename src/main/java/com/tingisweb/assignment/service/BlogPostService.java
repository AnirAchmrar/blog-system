package com.tingisweb.assignment.service;

import com.tingisweb.assignment.dto.BlogPostDto;
import org.springframework.data.domain.Page;

public interface BlogPostService {

    BlogPostDto save(BlogPostDto dto);
    BlogPostDto findById(Long id);
    BlogPostDto update(Long id, BlogPostDto dto);
    void delete(Long id);
    Page<BlogPostDto> findAll(Integer page, Integer size);

    Page<BlogPostDto> findBlogPostsByAuthorId(Integer page, Integer size);

}
