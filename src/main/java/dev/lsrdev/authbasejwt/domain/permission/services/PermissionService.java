package dev.lsrdev.authbasejwt.domain.permission.services;

import dev.lsrdev.authbasejwt.commons.generic.services.GenericSerice;
import dev.lsrdev.authbasejwt.domain.permission.entities.Permission;

public interface PermissionService extends GenericSerice<Permission> {
    boolean existsByName(String name);

    Permission getPermissionByName(String name);

}
