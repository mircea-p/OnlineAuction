package com.sda.onlineAuction.service;

import com.sda.onlineAuction.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class ProductDtoValidator {

    public void validate(ProductDto productDto, BindingResult bindingResult) {
        String priceAsString = productDto.getStartBidingPrice();
        try {
            Integer priceAsInteger = Integer.valueOf(priceAsString);
            if (priceAsInteger <= 0) {
                FieldError fieldError = new FieldError("productDto", "startBidingPrice", "Price must be positive!");
                bindingResult.addError(fieldError);
            }
        } catch (NumberFormatException numberFormatException) {
            FieldError fieldError = new FieldError("productDto", "startBidingPrice", "Price must be a number!");
            bindingResult.addError(fieldError);

        }
    }
}
