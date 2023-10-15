package dev.lsrdev.authbasejwt.domain.user.adapters;

import dev.lsrdev.authbasejwt.domain.user.dto.UserDTO;
import dev.lsrdev.authbasejwt.domain.user.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserAdapter {

    UserDTO toDto(User user);

    default List<UserDTO> toDtos(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .toList();
    }

    User toEntity(UserDTO userDTO);

    default List<User> toEntities(List<UserDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .toList();
    }

}
