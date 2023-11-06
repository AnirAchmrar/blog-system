package com.tingisweb.assignment.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * An entity class representing a blog post.
 */
@Entity
@Table(name = "blog_posts")
@Data
@NoArgsConstructor
public class BlogPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The title of the blog post.
     */
    private String title;
    /**
     * The content of the blog post stored as TEXT.
     */
    @Column(columnDefinition = "TEXT")
    private String content;
    /**
     * The publication date of the blog post.
     */
    @Column(name = "publication_date")
    private LocalDateTime publicationDate;
    /**
     * The author of the blog post, represented as a UserEntity object.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="author_id", nullable=false)
    private UserEntity author;

}
