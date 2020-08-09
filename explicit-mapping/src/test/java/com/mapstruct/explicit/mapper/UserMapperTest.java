package com.mapstruct.explicit.mapper;

import com.mapstruct.explicit.config.ApplicationProperties;
import com.mapstruct.explicit.enumeration.Gender;
import com.mapstruct.explicit.enumeration.PhoneType;
import com.mapstruct.explicit.model.AddressDTO;
import com.mapstruct.explicit.model.PhoneDTO;
import com.mapstruct.explicit.model.UserDTO;
import com.mapstruct.explicit.model.entity.Address;
import com.mapstruct.explicit.model.entity.Phone;
import com.mapstruct.explicit.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ApplicationProperties applicationProperties;

    @Test
    void toEntityTest() {
        AddressDTO addressDTO = AddressDTO.builder()
                .city("Melbourne")
                .street("Sad Lane 41")
                .zip(54389)
                .build();

        Set<AddressDTO> addresses = Stream.of(addressDTO).collect(Collectors.toSet());

        PhoneDTO phoneDTO = PhoneDTO.builder()
                .number("+662343756")
                .phoneType(PhoneType.LAND_LINE.name())
                .build();

        List<PhoneDTO> phones = Arrays.asList(phoneDTO);

        UserDTO userDTO = UserDTO.builder()
                .firstName("Tom")
                .lastName("Cruise")
                .email("tom@gamil.com")
                .gender("MALE")
                .addressList(addresses)
                .job("Actor")
                .registerDate("2020-08-08 17:40")
                .phones(phones)
                .build();

        User user = this.userMapper.toEntity(userDTO);
        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getGender(), user.getGender().name());
        assertEquals(userDTO.getJob(), user.getProfession());
        assertEquals(userDTO.getRegisterDate(), user.getRegisterDate()
                .format(DateTimeFormatter.ofPattern(this.applicationProperties.getDataTimeFormat())));
        assertNotNull(user.getAddresses());
        assertEquals(addressDTO.getId(), user.getAddresses().iterator().next().getId());
        assertEquals(addressDTO.getCity(), user.getAddresses().iterator().next().getCity());
        assertEquals(addressDTO.getStreet(), user.getAddresses().iterator().next().getStreet());
        assertEquals(addressDTO.getZip(), user.getAddresses().iterator().next().getZip());
        assertEquals(phoneDTO.getNumber(), user.getPhones().iterator().next().getNumber());
        assertEquals(phoneDTO.getPhoneType(), user.getPhones().iterator().next().getPhoneType().name());
        assertNull(user.getAddresses().iterator().next().getUser());
    }

    @Test
    void toDtoTest() {
        Address address = Address.builder()
                .id(1L)
                .city("Melbourne")
                .street("Sad Lane 41")
                .zip(54389)
                .build();

        Set<Address> addresses = Stream.of(address).collect(Collectors.toSet());

        Phone phone = Phone.builder()
                .number("+662343756")
                .phoneType(PhoneType.LAND_LINE)
                .build();

        Set<Phone> phones = Stream.of(phone).collect(Collectors.toSet());

        User user = User.builder()
                .id(1L)
                .firstName("Tom")
                .lastName("Cruise")
                .email("tom@gamil.com")
                .gender(Gender.MALE)
                .addresses(addresses)
                .profession("Actor")
                .registerDate(LocalDateTime.of(2020, 8, 8, 17, 40))
                .phones(phones)
                .build();

        UserDTO dto = this.userMapper.toDto(user);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getFirstName(), dto.getFirstName());
        assertEquals(user.getLastName(), dto.getLastName());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getGender().name(), dto.getGender());
        assertEquals(user.getProfession(), dto.getJob());
        assertEquals(user.getRegisterDate().format(DateTimeFormatter
                .ofPattern(this.applicationProperties.getDataTimeFormat())), dto.getRegisterDate());
        assertEquals(user.getFirstName()+" "+user.getLastName(), dto.getName());
        assertNotNull(dto.getAddressList());
        assertEquals(address.getId(), dto.getAddressList().iterator().next().getId());
        assertEquals(address.getCity(), dto.getAddressList().iterator().next().getCity());
        assertEquals(address.getStreet(), dto.getAddressList().iterator().next().getStreet());
        assertEquals(address.getZip(), dto.getAddressList().iterator().next().getZip());
        assertNull(dto.getAddressList().iterator().next().getUser());
        assertEquals(phone.getId(), dto.getPhones().iterator().next().getId());
        assertEquals(phone.getNumber(), dto.getPhones().iterator().next().getNumber());
        assertEquals(phone.getPhoneType().name(), dto.getPhones().iterator().next().getPhoneType());

    }
}
