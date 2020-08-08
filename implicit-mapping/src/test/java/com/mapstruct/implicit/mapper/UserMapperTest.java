package com.mapstruct.implicit.mapper;

import com.mapstruct.implicit.enumeration.Gender;
import com.mapstruct.implicit.model.AddressDTO;
import com.mapstruct.implicit.model.UserDTO;
import com.mapstruct.implicit.model.entity.Address;
import com.mapstruct.implicit.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    void toEntityTest() {
        AddressDTO addressDTO = AddressDTO.builder()
                .city("Melbourne")
                .street("Sad Lane 41")
                .zip(54389)
                .build();

        Set<AddressDTO> addresses = Stream.of(addressDTO).collect(Collectors.toSet());

        UserDTO userDTO = UserDTO.builder()
                .firstName("Tom")
                .lastName("Cruise")
                .email("tom@gamil.com")
                .gender("MALE")
                .addresses(addresses)
                .job("Actor")
                .build();

        User user = this.userMapper.toEntity(userDTO);
        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getGender(), user.getGender().name());
        assertEquals(userDTO.getJob(), user.getProfession());
        assertNotNull(user.getAddresses());
        assertEquals(addressDTO.getId(), user.getAddresses().iterator().next().getId());
        assertEquals(addressDTO.getCity(), user.getAddresses().iterator().next().getCity());
        assertEquals(addressDTO.getStreet(), user.getAddresses().iterator().next().getStreet());
        assertEquals(addressDTO.getZip(), user.getAddresses().iterator().next().getZip());
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

        // Set<Address> addresses = new HashSet<>(Arrays.asList(address));
        Set<Address> addresses = Stream.of(address).collect(Collectors.toSet());

        User user = User.builder()
                .id(1L)
                .firstName("Tom")
                .lastName("Cruise")
                .email("tom@gamil.com")
                .gender(Gender.MALE)
                .addresses(addresses)
                .profession("Actor")
                .build();

        UserDTO dto = this.userMapper.toDto(user);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getFirstName(), dto.getFirstName());
        assertEquals(user.getLastName(), dto.getLastName());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getGender().name(), dto.getGender());
        assertEquals(user.getProfession(), dto.getJob());
        assertNotNull(dto.getAddresses());
        assertEquals(address.getId(), dto.getAddresses().iterator().next().getId());
        assertEquals(address.getCity(), dto.getAddresses().iterator().next().getCity());
        assertEquals(address.getStreet(), dto.getAddresses().iterator().next().getStreet());
        assertEquals(address.getZip(), dto.getAddresses().iterator().next().getZip());
        assertNull(dto.getAddresses().iterator().next().getUser());
    }
}
