package dev.lsrdev.authbasejwt.domain.user.entities;

import dev.lsrdev.authbasejwt.commons.generic.domain.GenericDomain;
import dev.lsrdev.authbasejwt.domain.permission.entities.Permission;
import dev.lsrdev.authbasejwt.domain.permission.entities.PermissionUser;
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
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "USERS",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "ID_USER", name = "PK_ID_USER"),
                @UniqueConstraint(columnNames = "EMAIL", name = "UNQ_EMAIL")
        }
)
public class User extends GenericDomain<User> implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_USER")
    private UUID id;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @OneToMany(
            fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},
            mappedBy = "user"
    )
    private Set<PermissionUser> permissionsUsers;

    public User() {
    }

    public User(UUID id, String email) {
        this.id = id;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public Set<Permission> getAuthorities() {
        Set<Permission> permissions = new HashSet<>();
        if (getPermissionsUsers() != null) {
            getPermissionsUsers().forEach(
                    permissionUser -> permissions.add(permissionUser.getPermission())

            );
        }
        return permissions;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Set<PermissionUser> getPermissionsUsers() {
        return permissionsUsers;
    }

    public void setPermissionsUsers(Set<PermissionUser> permissionUsers) {
        this.permissionsUsers = permissionUsers;
    }
}
