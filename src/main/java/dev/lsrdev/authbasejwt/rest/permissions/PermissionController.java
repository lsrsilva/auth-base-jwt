package dev.lsrdev.authbasejwt.rest.permissions;

import dev.lsrdev.authbasejwt.domain.permission.adapters.PermissionAdapter;
import dev.lsrdev.authbasejwt.domain.permission.services.PermissionService;
import dev.lsrdev.authbasejwt.rest.JsonResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/permission")
public class PermissionController {
    private final PermissionService permissionService;
    private final PermissionAdapter permissionAdapter;

    public PermissionController(PermissionService permissionService, PermissionAdapter permissionAdapter) {
        this.permissionService = permissionService;
        this.permissionAdapter = permissionAdapter;
    }

    @GetMapping
    public ResponseEntity<JsonResponseDTO> list() {
        return ResponseEntity.ok(
                JsonResponseDTO.ok(
                        this.permissionAdapter.toDtos(
                                this.permissionService.findAll()
                        )
                )
        );
    }
}
