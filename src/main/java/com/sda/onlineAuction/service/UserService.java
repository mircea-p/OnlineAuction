package com.sda.onlineAuction.service;

import com.sda.onlineAuction.dto.UserDto;
import com.sda.onlineAuction.mapper.UserMapper;
import com.sda.onlineAuction.model.User;
import com.sda.onlineAuction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public void add(UserDto userDto) {

        User user = userMapper.mapp(userDto);
        userRepository.save(user);

    }


}
