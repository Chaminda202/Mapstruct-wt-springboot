package com.mapstruct.explicit.mapper;

import com.mapstruct.explicit.model.UserDTO;
import com.mapstruct.explicit.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DateMapper.class}, componentModel = "spring") //format date according to custom format (not a default format yyyy-MM-dd'T'HH:mm:ss)
public interface UserMapper {
    @Mapping(source = "job", target = "profession")
    @Mapping(source = "addressList", target = "addresses")
    User toEntity(UserDTO userDTO);
    @Mapping(source = "profession", target = "job")
    @Mapping(source = "addresses", target = "addressList")
    UserDTO toDto(User user);
}
