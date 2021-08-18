package com.sda.onlineAuction.mapper;

import com.sda.onlineAuction.dto.UserDto;
import com.sda.onlineAuction.model.User;
import com.sda.onlineAuction.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User mapp(UserDto userDto){
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        String passwordCoded = bCryptPasswordEncoder.encode(userDto.getPassword());

        user.setPassword(passwordCoded);
        user.setUserRole(UserRole.valueOf(userDto.getUserRole()));
        return user;
    }
}
