package com.tingisweb.assignment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * A Data Transfer Object (DTO) representing a blog post.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogPostDto {

    private Long id;
    /**
     * The title of the blog post. It is mandatory.
     */
    @NotBlank(message = "title is mandatory")
    private String title;
    /**
     * The publication date of the blog post in the format "yyyy-MM-dd HH:mm".
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime publicationDate;
    /**
     * The content of the blog post. It is mandatory.
     */
    @NotBlank(message = "content is mandatory")
    private String content;
    /**
     * The author of the blog post represented as an AuthorDto object.
     */
    private AuthorDto author;

}
