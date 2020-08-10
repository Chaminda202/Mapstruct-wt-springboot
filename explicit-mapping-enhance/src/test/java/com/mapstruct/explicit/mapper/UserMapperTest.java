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
import com.mapstruct.explicit.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
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
    @Autowired
    private UserRepository userRepository;

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
                .phoneType(PhoneType.LAND_LINE.getDescription())
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
                .salary(300.45)
                .build();

        User user = this.userMapper.toEntity(userDTO);
        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getGender(), user.getGender().name());
        assertEquals(userDTO.getJob(), user.getProfession());
        assertEquals(userDTO.getSalary(), user.getSalary().doubleValue());
        assertEquals(userDTO.getRegisterDate(), user.getRegisterDate()
                .format(DateTimeFormatter.ofPattern(this.applicationProperties.getDataTimeFormat())));
        assertNotNull(user.getAddresses());
        assertEquals(addressDTO.getId(), user.getAddresses().iterator().next().getId());
        assertEquals(addressDTO.getCity(), user.getAddresses().iterator().next().getCity());
        assertEquals(addressDTO.getStreet(), user.getAddresses().iterator().next().getStreet());
        assertEquals(addressDTO.getZip(), user.getAddresses().iterator().next().getZip());
        assertEquals(phoneDTO.getNumber(), user.getPhones().iterator().next().getNumber());
        assertEquals(phoneDTO.getPhoneType(), user.getPhones().iterator().next().getPhoneType().getDescription());
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
                .salary(new BigDecimal("345.50"))
                .build();

        UserDTO dto = this.userMapper.toDto(user);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getFirstName(), dto.getFirstName());
        assertEquals(user.getLastName(), dto.getLastName());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getGender().name(), dto.getGender());
        assertEquals(user.getProfession(), dto.getJob());
        assertEquals(user.getSalary().doubleValue(), dto.getSalary());
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
        assertEquals(phone.getPhoneType().getDescription(), dto.getPhones().iterator().next().getPhoneType());
    }

    @Test
    void toUpdateEntityTest() {
        AddressDTO addressDTO = AddressDTO.builder()
                .city("Melbourne")
                .street("Sad Lane 41")
                .zip(54389)
                .build();

        Set<AddressDTO> addresses = Stream.of(addressDTO).collect(Collectors.toSet());

        PhoneDTO phoneDTO = PhoneDTO.builder()
                .number("+662343756")
                .phoneType(PhoneType.LAND_LINE.getDescription())
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
                .salary(300.45)
                .build();

        User user = this.userRepository.save(this.userMapper.toEntity(userDTO));
        assertNotNull(user.getId());

        // define update values
        AddressDTO updateAddressDTO = AddressDTO.builder()
                .id(user.getAddresses().iterator().next().getId())
                .city("Melbourne update")
                .street("Sad Lane  update")
                .zip(54389)
                .build();

        Set<AddressDTO> updateAddresses = Stream.of(updateAddressDTO).collect(Collectors.toSet());

        PhoneDTO updatePhoneDTO = PhoneDTO.builder()
                .id(user.getPhones().iterator().next().getId())
                .number("+662343758")
                .phoneType(PhoneType.MOBILE.getDescription())
                .build();

        List<PhoneDTO> updatePhones = Arrays.asList(updatePhoneDTO);

        UserDTO updateUserDTO = UserDTO.builder()
                .id(user.getId())
                .firstName("Tom Update")
                .lastName("Cruise")
                .email("tom@gamil.com")
                .gender("MALE")
                .addressList(updateAddresses)
                .job("Actor Update")
                .registerDate("2020-08-08 17:40")
                .phones(updatePhones)
                .salary(300.45)
                .build();

        User updateUserDetails = this.userMapper.toUpdateEntity(updateUserDTO, user);
        User updatedUser = this.userRepository.save(updateUserDetails);

        assertEquals(updateUserDTO.getId(), updatedUser.getId());
        assertEquals(updateUserDTO.getFirstName(), updatedUser.getFirstName());
        assertEquals(updateUserDTO.getLastName(), updatedUser.getLastName());
        assertEquals(updateUserDTO.getEmail(), updatedUser.getEmail());
        assertEquals(updateUserDTO.getGender(), updatedUser.getGender().name());
        assertEquals(updateUserDTO.getJob(), updatedUser.getProfession());
        assertEquals(updateUserDTO.getSalary(), updatedUser.getSalary().doubleValue());
        assertEquals(updateUserDTO.getRegisterDate(), updatedUser.getRegisterDate().format(DateTimeFormatter
                .ofPattern(this.applicationProperties.getDataTimeFormat())));
        assertNotNull(updatedUser.getAddresses());
        assertEquals(updateAddressDTO.getId(), updatedUser.getAddresses().iterator().next().getId());
        assertEquals(updateAddressDTO.getCity(), updatedUser.getAddresses().iterator().next().getCity());
        assertEquals(updateAddressDTO.getStreet(), updatedUser.getAddresses().iterator().next().getStreet());
        assertEquals(updateAddressDTO.getZip(), updatedUser.getAddresses().iterator().next().getZip());
        assertNull(updatedUser.getAddresses().iterator().next().getUser());
        assertEquals(updatePhoneDTO.getId(), updatedUser.getPhones().iterator().next().getId());
        assertEquals(updatePhoneDTO.getNumber(), updatedUser.getPhones().iterator().next().getNumber());
        assertEquals(updatePhoneDTO.getPhoneType(), updatedUser.getPhones().iterator().next().getPhoneType().getDescription());
    }
}
