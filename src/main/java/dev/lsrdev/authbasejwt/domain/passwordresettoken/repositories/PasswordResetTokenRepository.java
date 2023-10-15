package dev.lsrdev.authbasejwt.domain.passwordresettoken.repositories;


import dev.lsrdev.authbasejwt.domain.passwordresettoken.entities.PasswordResetToken;
import dev.lsrdev.authbasejwt.domain.user.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, UUID> {

    @Modifying
    @Transactional
    @Query("DELETE FROM PasswordResetToken prt WHERE prt.user.id = :idUser")
    void invalidateAllTokensBeforeSave(@Param("idUser") UUID idUser);

    @Modifying
    @Transactional
    @Query("UPDATE PasswordResetToken prt SET prt.valid = :valid")
    void updateValid(@Param("valid") Boolean valid);

    Optional<PasswordResetToken> findByTokenAndUser(UUID uuid, User user);

    Optional<PasswordResetToken> findByToken(UUID uuid);
}
