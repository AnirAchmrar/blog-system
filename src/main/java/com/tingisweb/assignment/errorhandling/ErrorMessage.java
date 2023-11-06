package com.tingisweb.assignment.errorhandling;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * A class representing an error message with status code, timestamp, and message.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ErrorMessage {

    /**
     * The HTTP status code of the error response.
     */
    private int statusCode;
    /**
     * The timestamp of when the error occurred.
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm a")
    private LocalDateTime timestamp;
    /**
     * The error message providing details about the error.
     */
    private String message;
}