package com.sda.onlineAuction.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userRole;
}
