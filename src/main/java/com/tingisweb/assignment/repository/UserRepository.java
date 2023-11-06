package com.tingisweb.assignment.repository;

import com.tingisweb.assignment.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing user entities in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    /**
     * Find a user entity by their username.
     *
     * @param userName The username of the user to find.
     * @return An optional containing the user entity if found, or empty if not found.
     */
    Optional<UserEntity> findUserByUsername(String userName);

}
