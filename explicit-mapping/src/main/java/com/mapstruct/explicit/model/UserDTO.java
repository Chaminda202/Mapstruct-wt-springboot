package com.mapstruct.explicit.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String name; // firstName+ " " + lastName
    private String gender;
    private String job;
    private String registerDate;
    private double salary;
    private Set<AddressDTO> addressList;
    private List<PhoneDTO> phones;
}
