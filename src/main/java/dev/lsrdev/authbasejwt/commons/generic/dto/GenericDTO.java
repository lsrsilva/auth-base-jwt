package dev.lsrdev.authbasejwt.commons.generic.dto;

import java.util.UUID;

public class GenericDTO {
    private UUID id;

    public GenericDTO() {
    }

    public GenericDTO(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
