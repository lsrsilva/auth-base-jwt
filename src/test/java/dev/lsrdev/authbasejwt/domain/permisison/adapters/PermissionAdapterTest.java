package dev.lsrdev.authbasejwt.domain.permisison.adapters;

import dev.lsrdev.authbasejwt.domain.permission.adapters.PermissionAdapter;
import dev.lsrdev.authbasejwt.domain.permission.dto.PermissionDTO;
import dev.lsrdev.authbasejwt.domain.permission.dto.PermissionUserDTO;
import dev.lsrdev.authbasejwt.domain.permission.entities.Permission;
import dev.lsrdev.authbasejwt.domain.permission.entities.PermissionUser;
import dev.lsrdev.authbasejwt.domain.permission.enums.Permissions;
import dev.lsrdev.authbasejwt.domain.user.dto.UserDTO;
import dev.lsrdev.authbasejwt.domain.user.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SpringBootTest
class PermissionAdapterTest {

    @Autowired
    private PermissionAdapter permissionAdapter;

    @Test
    void toDto() {
        User user = new User(UUID.randomUUID(), "email");
        Set<PermissionUser> permissionUsers = new HashSet<>();
        permissionUsers.add(new PermissionUser(UUID.randomUUID(), user));
        Permission permission = new Permission(UUID.randomUUID(), Permissions.ADM.getName(), Permissions.ADM.getDescription(), permissionUsers);
        permission.getPermissionsUsers().forEach(p -> p.setPermission(permission));
        PermissionDTO dto = permissionAdapter.toDto(permission);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(permission.getId(), dto.id());

    }

    @Test
    void toEntity() {
        UserDTO user = new UserDTO(UUID.randomUUID(), "email");
        Set<PermissionUserDTO> permissionUsers = new HashSet<>();
        permissionUsers.add(new PermissionUserDTO(UUID.randomUUID(), user));
        PermissionDTO permission = new PermissionDTO(UUID.randomUUID(), Permissions.ADM.getName(), Permissions.ADM.getDescription(), permissionUsers);
        Permission entity = permissionAdapter.toEntity(permission);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(entity.getId(), permission.id());

    }

}
