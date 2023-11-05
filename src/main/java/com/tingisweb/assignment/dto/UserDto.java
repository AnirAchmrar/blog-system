package com.tingisweb.assignment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Data Transfer Object (DTO) representing user data.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    /**
     * The username of the user. It is mandatory.
     */
    @NotBlank(message = "username is mandatory")
    private String username;
    /**
     * The password of the user. It is mandatory.
     */
    @NotBlank(message = "password is mandatory")
    private String password;
    /**
     * The first name of the user. It is mandatory.
     */
    @NotBlank(message = "firstname is mandatory")
    private String firstname;
    /**
     * The last name of the user. It is mandatory.
     */
    @NotBlank(message = "lastname is mandatory")
    private String lastname;
    /**
     * The phone number of the user. It is mandatory.
     */
    @NotBlank(message = "phone is mandatory")
    private String phone;
    /**
     * The email address of the user. It is mandatory.
     */
    @NotBlank(message = "email is mandatory")
    private String email;

}
