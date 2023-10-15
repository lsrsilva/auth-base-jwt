package dev.lsrdev.authbasejwt.domain.user.dto;

import java.util.UUID;

public record UserDTO(UUID id, String email) {
}
