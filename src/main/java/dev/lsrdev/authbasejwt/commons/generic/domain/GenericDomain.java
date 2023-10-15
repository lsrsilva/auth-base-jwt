package dev.lsrdev.authbasejwt.commons.generic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.lsrdev.authbasejwt.commons.utils.CustomJsonDateDeserializer;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public abstract class GenericDomain<T extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = -8362232075708470363L;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "pt-BR", timezone = "Brazil/East")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", updatable = false)
    protected LocalDateTime createdAt;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "pt-BR", timezone = "Brazil/East")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setCreatedAt(LocalDateTime createdAt) {
        if (getId() == null) {
            createdAt = LocalDateTime.now();
        }
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public abstract UUID getId();

    public abstract void setId(UUID id);

    public boolean updateValues(T actual) {
        boolean canUpdate = false;

        verifyCanUpdate(actual, canUpdate);

        return canUpdate;
    }

    protected void verifyCanUpdate(T actual, boolean canUpdate) {
    }


}
