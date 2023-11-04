package com.tingisweb.assignment.repository;

import com.tingisweb.assignment.entity.BlogPostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPostEntity,Long> {

    Page<BlogPostEntity> findBlogPostsByAuthorId(Long authorId, Pageable pageable);

}
