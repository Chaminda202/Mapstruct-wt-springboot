package com.mapstruct.explicit.mapper;

import com.mapstruct.explicit.enumeration.PhoneType;
import com.mapstruct.explicit.model.PhoneDTO;
import com.mapstruct.explicit.model.entity.Phone;
import org.mapstruct.Mapper;

import java.util.Arrays;

@Mapper(componentModel = "spring", uses = {DateMapper.class, UserMapper.class})
public interface PhoneMapper {
    Phone toEntity(PhoneDTO phoneDTO);
    PhoneDTO toDto(Phone phone);

    default String phoneTypeMap(PhoneType phoneType) {
        return phoneType.getDescription();
    }

    default PhoneType phoneTypeMap(String phone) {
       return Arrays.stream(PhoneType.values()).filter(item -> item.getDescription().equals(phone)).findFirst().orElse(null);
    }
}
