package com.sda.onlineAuction.controller;

import com.sda.onlineAuction.dto.UserDto;
import com.sda.onlineAuction.service.UserService;
import com.sda.onlineAuction.validator.UserDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class UserController {

    @Autowired
    private UserDtoValidator userDtoValidator;
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "registrationX";
    }

    @PostMapping(value = "/registration")
    public String postRegistrationPage(Model model, UserDto userDto, BindingResult bindingResult) {
        userDtoValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registrationX";
        }
        userService.add(userDto);
        return "redirect:/home";
    }

    @GetMapping(value = "/login")
    public String getLoginPage(Model model) {

        return "loginX";
    }

    @GetMapping(value = "/login-error")
    public String getLoginErrorPage(Model model) {
        model.addAttribute("loginError", true);
        return "loginX";
    }
}

