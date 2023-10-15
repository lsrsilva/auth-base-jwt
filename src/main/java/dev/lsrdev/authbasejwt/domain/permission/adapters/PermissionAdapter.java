package dev.lsrdev.authbasejwt.domain.permission.adapters;

import dev.lsrdev.authbasejwt.domain.permission.dto.PermissionDTO;
import dev.lsrdev.authbasejwt.domain.permission.entities.Permission;
import io.jsonwebtoken.lang.Collections;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = PermissionUserAdapter.class)
public interface PermissionAdapter {

    PermissionDTO toDto(Permission permisison);

    Permission toEntity(PermissionDTO permissionDTO);

    @BeforeMapping
    default void resolveStackOverflow(Permission permission) {
        if (!Collections.isEmpty(permission.getPermissionsUsers())) {
            permission.getPermissionsUsers().forEach(
                    permissionUser -> permissionUser.setPermission(null)
            );
        }
    }

    default List<PermissionDTO> toDtos(List<Permission> permissions) {
        return permissions.stream()
                .map(this::toDto)
                .toList();
    }

    default Set<PermissionDTO> toDtosSet(Set<Permission> permissions) {
        return permissions.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }


    default List<Permission> toEntities(List<PermissionDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .toList();
    }
}
