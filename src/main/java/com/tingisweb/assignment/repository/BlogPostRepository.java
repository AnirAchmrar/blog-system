package com.tingisweb.assignment.repository;

import com.tingisweb.assignment.entity.BlogPostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing blog post entities in the database.
 */
@Repository
public interface BlogPostRepository extends JpaRepository<BlogPostEntity,Long> {

    /**
     * Find a page of blog post entities by the author's ID.
     *
     * @param authorId The ID of the author for whom to find blog posts.
     * @param pageable The paging and sorting information for the result.
     * @return A page of blog post entities written by the specified author.
     */
    Page<BlogPostEntity> findBlogPostsByAuthorId(Long authorId, Pageable pageable);

}
