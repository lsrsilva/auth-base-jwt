package dev.lsrdev.authbasejwt.domain.permission.entities;

import dev.lsrdev.authbasejwt.commons.generic.domain.GenericDomain;
import dev.lsrdev.authbasejwt.domain.user.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.UUID;

@Entity
@Table(name = "PERMISSIONS_USERS",
        uniqueConstraints = @UniqueConstraint(columnNames = "ID_PERMISSION_USER", name = "PK_PERMI_USER_ID"))
public class PermissionUser extends GenericDomain<PermissionUser> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_PERMISSION_USER")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ID_USER", foreignKey = @ForeignKey(name = "FK_PERM_USER_ID_USER"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "ID_PERMISSION", foreignKey = @ForeignKey(name = "FK_PERM_USER_ID_PERMISSION"))
    private Permission permission;

    public PermissionUser() {
    }

    public PermissionUser(UUID id, User user) {
        this.id = id;
        this.user = user;
    }

    public PermissionUser(User user, Permission permission) {
        this.user = user;
        this.permission = permission;
    }

    public PermissionUser(UUID id, User user, Permission permission) {
        this.id = id;
        this.user = user;
        this.permission = permission;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
}