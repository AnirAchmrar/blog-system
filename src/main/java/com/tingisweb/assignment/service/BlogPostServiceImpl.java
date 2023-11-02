package com.tingisweb.assignment.service;

import com.tingisweb.assignment.dto.AuthorDto;
import com.tingisweb.assignment.dto.BlogPostDto;
import com.tingisweb.assignment.entity.BlogPostEntity;
import com.tingisweb.assignment.entity.UserEntity;
import com.tingisweb.assignment.errorhandling.exception.*;
import com.tingisweb.assignment.mapper.BlogPostMapper;
import com.tingisweb.assignment.repository.BlogPostRepository;
import com.tingisweb.assignment.security.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Validated
public class BlogPostServiceImpl implements BlogPostService{

    private final BlogPostMapper blogPostMapper;
    private final BlogPostRepository blogPostRepository;
    private final SecurityService securityService;
    public static final String BLOG_POST_NOT_FOUND = "Blog post not found!";

    @Override
    @Transactional
    public BlogPostDto save(BlogPostDto dto) {
        if (dto != null && dto.getTitle() != null && dto.getContent() != null){
            dto.setId(null);
            UserEntity currentUser = securityService.getAuthenticatedUser();
            dto.setAuthor(new AuthorDto(currentUser.getId(),
                    currentUser.getFirstname()+" "+currentUser.getLastname()));
            dto.setPublicationDate(LocalDateTime.now());
            BlogPostEntity entity = blogPostMapper.toEntity(dto);
            return blogPostMapper.toDto(blogPostRepository.save(entity));
        }else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public BlogPostDto findById(Long id) {
        BlogPostEntity entity = blogPostRepository.findById(id).orElseThrow(()->
                new ObjectNotFoundException(BLOG_POST_NOT_FOUND));
        return blogPostMapper.toDto(entity);
    }

    @Override
    @Transactional
    public BlogPostDto update(Long id, BlogPostDto dto) {
        if (id == null || id.toString().isEmpty()){
            throw new MissingIdException();
        } else if (dto == null) {
            throw new IllegalArgumentException();
        } else if (!id.equals(dto.getId())) {
            throw new EditAnotherEntityException();
        }else {
            currentUserAuthorizedForAction(id);
            if(blogPostRepository.findById(id).isEmpty()){
                throw new ObjectNotFoundException(BLOG_POST_NOT_FOUND);
            }else {
                BlogPostEntity entity = blogPostMapper.toEntity(dto);
                entity = blogPostRepository.save(entity);
                return blogPostMapper.toDto(entity);
            }
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(id == null || id.toString().isEmpty()){
            throw new MissingIdException();
        }else {
            currentUserAuthorizedForAction(id);
            if(blogPostRepository.findById(id).isEmpty()){
                throw new ObjectNotFoundException(BLOG_POST_NOT_FOUND);
            }else {
                blogPostRepository.deleteById(id);
            }
        }
    }

    @Override
    public Page<BlogPostDto> findAll(Integer page, Integer size) {
        page = page != null && page >= 1 ? page -1 : 0;
        size = size != null && size >= 5 ? size : 5;
        Pageable pageRequest = PageRequest.of(page,size,
                Sort.by(Sort.Order.desc("publicationDate")));
        Page<BlogPostEntity> entitiesPage = blogPostRepository.findAll(pageRequest);
        if (!entitiesPage.getContent().isEmpty()){
            return blogPostMapper.toPageDto(entitiesPage);
        }else {
            throw new NoContentException();
        }
    }

    public void currentUserAuthorizedForAction(Long blogPostId) {
        BlogPostEntity blogPostEntity = blogPostRepository
                .findById(blogPostId).orElseThrow(()->
                        new ObjectNotFoundException(BLOG_POST_NOT_FOUND));
        UserEntity currentUser = securityService.getAuthenticatedUser();
        if(!blogPostEntity.getAuthor().getId().equals(currentUser.getId())){
            throw new UnauthorizedException("Unauthorized action");
        }
    }

}
