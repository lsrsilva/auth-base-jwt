package dev.lsrdev.authbasejwt.domain.passwordresettoken.entities;


import dev.lsrdev.authbasejwt.domain.user.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "PASSWORD_RESET_TOKEN")
public class PasswordResetToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_PSW_RESET_TKN")
    private UUID id;

    @Column(name = "TOKEN", nullable = false)
    private UUID token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_USER", nullable = false, foreignKey = @ForeignKey(name = "FK_PSW_RESET_TKN_ID_USER"))
    private User user;

    @Column(name = "EXPIRY_DATE", nullable = false)
    private LocalDateTime expiryDate;

    @Column(name = "VALID", nullable = false)
    private Boolean valid;

    public PasswordResetToken() {
        this.token = UUID.randomUUID();
        this.expiryDate = LocalDateTime.now().plusMinutes(EXPIRATION);
    }

    public PasswordResetToken(User user) {
        this();
        this.user = user;
        this.valid = true;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

}
