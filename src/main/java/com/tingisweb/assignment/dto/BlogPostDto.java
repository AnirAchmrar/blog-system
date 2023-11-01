package com.tingisweb.assignment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogPostDto {

    private Long id;
    @NotBlank(message = "title is mandatory")
    private String title;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime publicationDate;
    @NotBlank(message = "content is mandatory")
    private String content;
    private AuthorDto author;

}
