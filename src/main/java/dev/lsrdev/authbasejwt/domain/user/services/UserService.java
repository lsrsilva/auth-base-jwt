package dev.lsrdev.authbasejwt.domain.user.services;

import dev.lsrdev.authbasejwt.domain.auth.dto.ChangePasswordDTO;
import dev.lsrdev.authbasejwt.domain.user.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User save(User user);

    User findById(UUID id);

    User update(User user);

    void deleteById(UUID id);

    void delete(User user);

    List<User> findAll();

    Optional<User> findByEmail(String username);

    void changePassword(ChangePasswordDTO changePasswordDTO);

    void sendResetPasswordEmail(String url, String email);

}
