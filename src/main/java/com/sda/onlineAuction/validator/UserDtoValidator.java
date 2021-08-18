package com.sda.onlineAuction.validator;

import com.sda.onlineAuction.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class UserDtoValidator {

    public void validate(UserDto userDto, BindingResult bindingResult) {
        String password = userDto.getPassword();
        if (password.length() < 6){
            FieldError fieldError = new FieldError("userDto", "password", "Password must have at least 6 caracters!");
            bindingResult.addError(fieldError);
        }
    }
}
