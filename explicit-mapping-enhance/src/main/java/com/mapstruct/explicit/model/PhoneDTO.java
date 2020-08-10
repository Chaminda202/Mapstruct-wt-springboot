package com.mapstruct.explicit.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class PhoneDTO {
    private Integer id;
    private String number;
    private String phoneType;
    private UserDTO user;
}