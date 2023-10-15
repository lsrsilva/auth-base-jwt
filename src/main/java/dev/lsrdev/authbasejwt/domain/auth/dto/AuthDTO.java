package dev.lsrdev.authbasejwt.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.lsrdev.authbasejwt.domain.permission.dto.PermissionDTO;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthDTO {
    private UUID idUser;

    private String token;

    private String name;

    private String refreshToken;

    private Set<PermissionDTO> permissions;

    private String profilePhoto;

    public AuthDTO() {
    }

    public AuthDTO(UUID idUser, String token) {
        this.idUser = idUser;
        this.token = token;
    }

    public AuthDTO(UUID idUser, String token, String name) {
        this.idUser = idUser;
        this.token = token;
        this.name = name;
    }

    public AuthDTO(UUID idUser, String token, Set<PermissionDTO> permissions, String name) {
        this(idUser, token, permissions, name, null);
    }

    public AuthDTO(UUID idUser, String token, Set<PermissionDTO> permissions, String name, String profilePhoto) {
        this.idUser = idUser;
        this.token = token;
        this.name = name;
        this.profilePhoto = profilePhoto;
        Set<PermissionDTO> permissionDTOS = new HashSet<>();
        permissions.forEach(
                permission -> permissionDTOS.add(new PermissionDTO(permission.id(), permission.name()))
        );
        this.permissions = permissionDTOS;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<PermissionDTO> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionDTO> permissions) {
        this.permissions = permissions;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

}
