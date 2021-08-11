package com.sda.onlineAuction.controller;

import com.sda.onlineAuction.dto.UserDto;
import com.sda.onlineAuction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/addUser")
    public String getAddUserPage(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "addUser";
    }

    @PostMapping("/addUser")
    public String PostAddUserPage(UserDto userDto){
        userService.add(userDto);
        return "addUser";
    }
}
