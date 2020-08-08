package com.mapstruct.implicit.mapper;

import com.mapstruct.implicit.model.UserDTO;
import com.mapstruct.implicit.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);
    UserDTO toDto(User user);
}
