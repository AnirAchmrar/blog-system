package com.tingisweb.assignment.mapper;

import com.tingisweb.assignment.dto.AuthorDto;
import com.tingisweb.assignment.dto.BlogPostDto;
import com.tingisweb.assignment.entity.BlogPostEntity;
import com.tingisweb.assignment.errorhandling.exception.ObjectNotFoundException;
import com.tingisweb.assignment.repository.BlogPostRepository;
import com.tingisweb.assignment.repository.UserRepository;
import java.util.Collections;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BlogPostMapper implements GenericMapper<BlogPostEntity, BlogPostDto>{

    private final UserRepository userRepository;
    private final BlogPostRepository blogPostRepository;
    public static final String USER_NOT_FOUND = "User not found!";
    public static final String BLOG_POST_NOT_FOUND = "Blog post not found!";

    @Override
    public BlogPostDto toDto(BlogPostEntity entity) {
        if (entity == null){
            return null;
        }
        BlogPostDto dto = new BlogPostDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setPublicationDate(entity.getPublicationDate());
        dto.setAuthor(new AuthorDto(entity.getAuthor().getId(),
                entity.getAuthor().getFirstname()+" "+entity.getAuthor().getLastname()));
        dto.setContent(entity.getContent());
        return dto;
    }

    @Override
    public BlogPostEntity toEntity(BlogPostDto dto) {
        if (dto == null){
            return null;
        }
        BlogPostEntity entity;
        if(dto.getId() != null){
            entity = blogPostRepository.findById(dto.getId()).orElseThrow(()->
                    new ObjectNotFoundException(BLOG_POST_NOT_FOUND));
        }else{
            entity = new BlogPostEntity();
        }
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setPublicationDate(dto.getPublicationDate());
        entity.setAuthor(userRepository.findById(dto.getAuthor().getId()).orElseThrow(()->
                new ObjectNotFoundException(USER_NOT_FOUND)));
        entity.setContent(dto.getContent());
        return entity;
    }

    @Override
    public Page<BlogPostDto> toPageDto(Page<BlogPostEntity> entities) {
        if(entities != null){
            return entities.map(this::toDto);
        }else {
            return Page.empty();
        }
    }

    @Override
    public List<BlogPostDto> toListDto(List<BlogPostEntity> entities) {
        if(entities != null){
            return entities.stream().map(this::toDto).toList();
        }else {
            return Collections.emptyList();
        }
    }
}
