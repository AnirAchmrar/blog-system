package com.tingisweb.assignment.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * An entity class representing a user in the system.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The username of the user. It is unique.
     */
    @Column(unique = true)
    private String username;
    /**
     * The password of the user.
     */
    private String password;
    /**
     * The first name of the user.
     */
    private String firstname;
    /**
     * The last name of the user.
     */
    private String lastname;
    /**
     * The phone number of the user.
     */
    private String phone;
    /**
     * The email address of the user. It is unique.
     */
    @Column(unique = true)
    private String email;
    /**
     * A list of blog posts authored by this user.
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<BlogPostEntity> posts = new ArrayList<>();

}
