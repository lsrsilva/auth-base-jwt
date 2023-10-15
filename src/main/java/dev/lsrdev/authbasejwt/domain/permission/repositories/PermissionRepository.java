package dev.lsrdev.authbasejwt.domain.permission.repositories;

import dev.lsrdev.authbasejwt.commons.generic.repositories.GenericRepository;
import dev.lsrdev.authbasejwt.domain.permission.entities.Permission;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PermissionRepository extends GenericRepository<Permission, UUID> {
    boolean existsByName(String name);

    Permission getPermissionByName(String name);

}
