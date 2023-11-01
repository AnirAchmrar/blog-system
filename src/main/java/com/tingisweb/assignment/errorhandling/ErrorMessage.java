package com.tingisweb.assignment.errorhandling;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ErrorMessage {

    private int statusCode;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm a")
    private LocalDateTime timestamp;
    private String message;
}