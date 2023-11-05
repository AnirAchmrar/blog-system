package com.tingisweb.assignment.mapper;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * A generic interface for mapping (converting) between entity and DTO objects.
 *
 * @param <E> The entity type.
 * @param <D> The DTO type.
 */
public interface GenericMapper <E,D>{

    /**
     * Converts an entity object to a DTO object.
     *
     * @param entity The entity object to be converted.
     * @return The DTO object.
     */
    D toDto(E entity);

    /**
     * Converts a DTO object to an entity object.
     *
     * @param dto The DTO object to be converted.
     * @return The entity object.
     */
    E toEntity(D dto);

    /**
     * Converts a page of entity objects to a page of DTO objects.
     *
     * @param entities The page of entity objects to be converted.
     * @return A page of DTO objects.
     */
    Page<D> toPageDto(Page<E> entities);

    /**
     * Converts a list of entity objects to a list of DTO objects.
     *
     * @param entities The list of entity objects to be converted.
     * @return A list of DTO objects.
     */
    List<D> toListDto(List<E> entities);

}
