package dev.lsrdev.authbasejwt.domain.permission.adapters;

import dev.lsrdev.authbasejwt.domain.permission.dto.PermissionUserDTO;
import dev.lsrdev.authbasejwt.domain.permission.entities.PermissionUser;
import dev.lsrdev.authbasejwt.domain.user.adapters.UserAdapter;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserAdapter.class})
public interface PermissionUserAdapter {

    PermissionUserDTO toDto(PermissionUser permissionUser);

    default List<PermissionUserDTO> toDtos(List<PermissionUser> permissionsUsers) {
        return permissionsUsers.stream()
                .map(this::toDto)
                .toList();
    }

    PermissionUser toEntity(PermissionUserDTO permissionDTO);

    default List<PermissionUser> toEntities(List<PermissionUserDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .toList();
    }
}
