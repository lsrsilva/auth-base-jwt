package dev.lsrdev.authbasejwt.commons.generic.services;

import dev.lsrdev.authbasejwt.commons.generic.domain.GenericDomain;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenericSerice<T extends GenericDomain<T>> {
    T save(T entity);

    <S extends T> List<S> saveAll(Iterable<S> entities);

    void flush();

    <S extends T> S saveAndFlush(S entity);

    void delete(T entity);

    void deleteById(UUID id);

    void deleteAll();

    void deleteInBatch(Iterable<T> entities);

    /**
     * Deletes all entities in a batch call.
     */
    void deleteAllInBatch();

    long count();

    List<T> findAll();

    List<T> findAll(Object value);

    Iterable<T> findAll(Sort sort);

    Page<T> findAll(Object searchObject, Integer page, Integer size, String paramSort, String sortDirection);

    <S extends T> List<S> findAll(Example<S> example);

    <S extends T> List<S> findAll(Example<S> example, Sort sort);

    List<T> findAllById(Iterable<UUID> ids);

    Optional<T> findById(UUID id);
}
