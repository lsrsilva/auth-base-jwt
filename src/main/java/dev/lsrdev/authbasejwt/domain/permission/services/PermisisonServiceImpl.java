package dev.lsrdev.authbasejwt.domain.permission.services;

import dev.lsrdev.authbasejwt.commons.generic.services.impl.GenericServiceImpl;
import dev.lsrdev.authbasejwt.domain.permission.entities.Permission;
import dev.lsrdev.authbasejwt.domain.permission.repositories.PermissionRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PermisisonServiceImpl extends GenericServiceImpl<Permission> implements PermissionService {
    private final PermissionRepository permissionRepository;
    private static final String PERMISSION_PREFIX = "ROLE_";

    public PermisisonServiceImpl(PermissionRepository permissionRepository) {
        super(permissionRepository);
        this.permissionRepository = permissionRepository;
    }

    @Override
    public boolean existsByName(String name) {
        if (!name.startsWith(PERMISSION_PREFIX)) {
            name = PERMISSION_PREFIX + name;
        }
        return permissionRepository.existsByName(name);
    }

    @Override
    public Permission getPermissionByName(String name) {
        if (!name.startsWith(PERMISSION_PREFIX)) {
            name = PERMISSION_PREFIX + name;
        }
        return permissionRepository.getPermissionByName(name);
    }

    @Override
    protected void beforeSave(Permission entity) {
        if (!entity.getName().startsWith(PERMISSION_PREFIX)) {
            entity.setName(PERMISSION_PREFIX + entity.getName());
        }
        if (permissionRepository.existsByName(entity.getName())) {
            LoggerFactory.getLogger(PermisisonServiceImpl.class).error("Permission with name {} already exists. Ignored.", entity.getName());
            return;
        }
        super.beforeSave(entity);
    }

}
