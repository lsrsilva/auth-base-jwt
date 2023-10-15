package dev.lsrdev.authbasejwt.commons.generic.services.impl;

import dev.lsrdev.authbasejwt.commons.annotations.DefaultSortProperty;
import dev.lsrdev.authbasejwt.commons.exceptions.HttpStatusException;
import dev.lsrdev.authbasejwt.commons.generic.domain.GenericDomain;
import dev.lsrdev.authbasejwt.commons.generic.repositories.GenericRepository;
import dev.lsrdev.authbasejwt.commons.generic.services.GenericSerice;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class GenericServiceImpl<T extends GenericDomain<T>> implements GenericSerice<T> {
    private final GenericRepository<T, UUID> repository;

    public GenericServiceImpl(GenericRepository<T, UUID> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public T save(T entity) {
        makeValidations(entity);
        beforeSave(entity);
        return repository.save(entity);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return repository.saveAndFlush(entity);
    }

    protected void beforeSave(T entity) {
        if (entity.getId() != null) {
            beforeUpdate(entity);
        } else {
            entity.setCreatedAt(LocalDateTime.now());
        }

    }

    protected void beforeUpdate(T editedEntity) {
        Optional<T> old = repository.findById(editedEntity.getId());
        T oldEntity = old.orElse(null);
        if (old.isPresent()) {
            if (editedEntity.getCreatedAt() == null) {
                editedEntity.setCreatedAt(oldEntity.getCreatedAt());
            }
            editedEntity.setUpdatedAt(LocalDateTime.now());
        } else {
            if (editedEntity.getCreatedAt() == null) {
                editedEntity.setCreatedAt(LocalDateTime.now());
            }
        }
    }

    protected void makeValidations(T entity) throws HttpStatusException {
        // Must be implemented on the child
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void delete(T entity) {
        this.repository.delete(entity);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteAll() {
        this.repository.deleteAll();
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteById(UUID id) {
        this.repository.deleteById(id);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteInBatch(Iterable<T> entities) {
        repository.deleteAllInBatch(entities);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }
    public long count() {
        return repository.count();
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAll(Object searchTerm) {
        if (searchTerm != null) {
            return repository.findAll(createSpec(searchTerm));
        }
        return repository.findAll();
    }

    @Override
    public Iterable<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    public Page<T> findAll(Object value, Integer page, Integer size, String sortProperty, String sortDirection) {
        try {
            if (sortProperty == null || sortProperty.equals("undefined") || sortProperty.equals("null")) {
                sortProperty = getDefaultSortProperty();
            }

            if (size == null) {
                size = 10;
            }

            if (page == null) {
                page = 0;
            }
            Sort.Direction direction = sortDirection != null && sortDirection.equalsIgnoreCase("asc") ?
                    Sort.Direction.ASC : Sort.Direction.DESC;
            PageRequest pageRequest = PageRequest.of(page, size, direction, sortProperty);
            if (value != null) {
                return repository.findAll(createSpec(value), pageRequest);
            }
            return repository.findAll(pageRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpStatusException("Erro interno no servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<T> findAllById(Iterable<UUID> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return repository.findAll(example);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return repository.findAll(example, sort);
    }

    @Override
    public Optional<T> findById(UUID id) {
        return repository.findById(id);
    }

    public String getDefaultSortProperty() {
        String paramSort = "";

        Field[] fields = getEntityClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(DefaultSortProperty.class)) {
                paramSort = field.getName();
                break;
            }
        }

        return paramSort;
    }

    public Specification<T> createSpec(Object searchTerm) {
        Field[] fields = getEntityClass().getDeclaredFields();
        Specification<T> spec = null;
        if (searchTerm instanceof LinkedHashMap) {
            LinkedHashMap<String, String> lhk = (LinkedHashMap<String, String>) searchTerm;
            AtomicReference<Specification<T>> finalSpec = new AtomicReference<>();
            lhk.keySet().forEach(
                    key -> finalSpec.set(createSpec(finalSpec.get(), lhk.get(key), key, true))
            );
            spec = finalSpec.get();
        } else {
            for (Field field : fields) {
                if (canCreateSpec(field, searchTerm)) {
                    spec = createSpec(spec, searchTerm, field.getName());
                }
            }
        }
        return spec;
    }

    private boolean canCreateSpec(Field field, Object searchTerm) {
        return field.getType().equals(String.class) ||
                (field.getType().getGenericSuperclass() != null && field.getType().getGenericSuperclass().equals(Number.class)) && searchTerm instanceof Number
                || field.getType().equals(Date.class) && searchTerm instanceof Date
                || field.getType().equals(LocalDate.class) && searchTerm instanceof LocalDate
                || field.getType().equals(LocalTime.class) && searchTerm instanceof LocalTime
                || field.getType().equals(LocalDateTime.class) && searchTerm instanceof LocalDateTime
                || field.getType().equals(Boolean.class) && searchTerm instanceof Boolean;
    }

    public Specification<T> createSpec(Specification<T> spec, Object searchValue, String searchProperty) {
        return createSpec(spec, searchValue, searchProperty, false);
    }

    public Specification<T> createSpec(Specification<T> spec, Object searchValue, String searchProperty, boolean withAnd) {
        if (spec == null) {
            return spec(searchValue, searchProperty);
        }

        if (withAnd) {
            return spec.and(spec(searchValue, searchProperty));
        }

        return spec.or(spec(searchValue, searchProperty));
    }

    private Specification<T> spec(Object searchValue, String searchProperty) {
        return (root, query, builder) -> {
            if (searchValue instanceof String value) {
                return builder.like(root.get(searchProperty), "%".concat(value).concat("%"));
            }
            return builder.equal(root.get(searchProperty), searchValue);
        };
    }
    @SuppressWarnings("unchecked")
    public Class<T> getEntityClass() {
        final ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }
}
