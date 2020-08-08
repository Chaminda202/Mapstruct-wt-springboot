package com.mapstruct.implicit.model;

import com.mapstruct.implicit.model.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AddressDTO {
    private Long id;
    private User user;
    private Integer zip;
    private String street;
    private String city;
}