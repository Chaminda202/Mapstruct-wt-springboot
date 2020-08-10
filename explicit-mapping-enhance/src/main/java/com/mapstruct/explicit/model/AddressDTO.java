package com.mapstruct.explicit.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AddressDTO {
    private Long id;
    private UserDTO user;
    private Integer zip;
    private String street;
    private String city;
}