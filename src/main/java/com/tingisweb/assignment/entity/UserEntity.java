package com.tingisweb.assignment.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    @Column(unique = true)
    private String email;
    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<BlogPostEntity> posts = new ArrayList<>();

}
