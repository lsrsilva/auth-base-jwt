package dev.lsrdev.authbasejwt.domain.permission.dto;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
public record PermissionDTO(UUID id, String name, String description, Set<PermissionUserDTO> permissionsUsers) {
    public PermissionDTO() {
        this(null, null, null, null);
    }
    public PermissionDTO(UUID id, String name) {
        this(id, name, null, null);
    }
}
