package com.mapstruct.implicit.mapper;

import com.mapstruct.implicit.model.UserDTO;
import com.mapstruct.implicit.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "job", target = "profession")
    User toEntity(UserDTO userDTO);
    @Mapping(source = "profession", target = "job")
    UserDTO toDto(User user);
}
