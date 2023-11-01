package com.tingisweb.assignment.mapper;

import org.springframework.data.domain.Page;

import java.util.List;

public interface GenericMapper <E,D>{

    D toDto(E entity);
    E toEntity(D dto);
    Page<D> toPageDto(Page<E> entities);
    List<D> toListDto(List<E> entities);

}
