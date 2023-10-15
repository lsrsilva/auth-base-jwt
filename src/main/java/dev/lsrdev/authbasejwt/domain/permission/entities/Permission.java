package dev.lsrdev.authbasejwt.domain.permission.entities;


import dev.lsrdev.authbasejwt.commons.generic.domain.GenericDomain;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "PERMISSIONS",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "ID_PERMISSION", name = "PK_ID_PERMISSION"),
                @UniqueConstraint(columnNames = "NAME", name = "UNK_PERM_NAME")
        }
)
public class Permission extends GenericDomain<Permission> implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_PERMISSION")
    private UUID id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
    @OneToMany(
            fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.MERGE},
            mappedBy = "permission"
    )
    private Set<PermissionUser> permissionsUsers;

    public Permission() {
    }

    public Permission(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Permission(UUID id, String name, String description, Set<PermissionUser> permissionsUsers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.permissionsUsers = permissionsUsers;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<PermissionUser> getPermissionsUsers() {
        return permissionsUsers;
    }

    public void setPermissionsUsers(Set<PermissionUser> permissionUserSet) {
        this.permissionsUsers = permissionUserSet;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
