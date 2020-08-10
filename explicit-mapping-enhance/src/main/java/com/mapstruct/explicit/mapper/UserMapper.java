package com.mapstruct.explicit.mapper;

import com.mapstruct.explicit.model.UserDTO;
import com.mapstruct.explicit.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(uses = {DateMapper.class, PhoneMapper.class}, componentModel = "spring") //format date according to custom format (not a default format yyyy-MM-dd'T'HH:mm:ss)
public abstract class UserMapper {
    @Mappings({
            @Mapping(source = "job", target = "profession"),
            @Mapping(source = "addressList", target = "addresses")
    })
    public abstract User toEntity(UserDTO userDTO);
    @Mapping(source = "profession", target = "job")
    @Mapping(source = "addresses", target = "addressList")
    @Mapping(target = "name", expression = "java(user.getFirstName() +\" \"+ user.getLastName())")
    public abstract UserDTO toDto(User user);

    @Mapping(source = "job", target = "profession")
    @Mapping(source = "addressList", target = "addresses")
    public abstract User toUpdateEntity(UserDTO userDTO, @MappingTarget User target);
}
