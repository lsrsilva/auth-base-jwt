package dev.lsrdev.authbasejwt.domain.permission.dto;

import dev.lsrdev.authbasejwt.domain.user.dto.UserDTO;

import java.util.UUID;

public record PermissionUserDTO(UUID id, UserDTO user, PermissionDTO permission) {
    public PermissionUserDTO() {
        this(null, null, null);
    }
    public PermissionUserDTO(UUID id, UserDTO user) {
        this(id, user, null);
    }
}
