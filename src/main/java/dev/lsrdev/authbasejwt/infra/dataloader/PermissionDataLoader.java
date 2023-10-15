package dev.lsrdev.authbasejwt.infra.dataloader;

import dev.lsrdev.authbasejwt.domain.permission.entities.Permission;
import dev.lsrdev.authbasejwt.domain.permission.enums.Permissions;
import dev.lsrdev.authbasejwt.domain.permission.services.PermissionService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PermissionDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final PermissionService permissionService;

    public PermissionDataLoader(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createPermissions();
    }

    private void createPermissions() {
        List<Permissions> permisisonsList = Arrays.asList(Permissions.values());
        permisisonsList.forEach(
                perm -> {
                    if (!permissionService.existsByName(perm.getName())) {
                        permissionService.save(new Permission(perm.getName(), perm.getDescription()));
                    }
                }
        );
    }
}

