package dev.lsrdev.authbasejwt.commons.generic.dto;

import dev.lsrdev.authbasejwt.commons.generic.domain.GenericDomain;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GenericAdapter<E extends GenericDomain<E>, D extends GenericDTO> {
    default D toDto(E e) {
        return this.toDto(e, false);
    }

    default D toDto(E e, boolean loadCollections) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    default E toEntity(D d) {
        return this.toEntity(d, false);
    }

    default E toEntity(D d, boolean loadCollections) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    default List<E> toEntities(List<D> ds) {
        return this.toEntities(ds, false);
    }

    default List<E> toEntities(List<D> ds, boolean loadCollections) {
        return ds.stream().map(d -> this.toEntity(d, loadCollections)).toList();
    }

    default List<D> toDtos(List<E> entities) {
        return this.toDtos(entities, false);
    }
    default List<D> toDtos(List<E> entities, boolean loadCollections) {
        return entities.stream().map(e -> this.toDto(e, loadCollections)).toList();
    }
}
