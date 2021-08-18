package com.sda.onlineAuction.controller;

import com.sda.onlineAuction.dto.ProductDto;
import com.sda.onlineAuction.dto.UserDto;
import com.sda.onlineAuction.service.UserService;
import com.sda.onlineAuction.validator.ProductDtoValidator;
import com.sda.onlineAuction.service.ProductService;
import com.sda.onlineAuction.validator.UserDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDtoValidator productDtoValidator;
    @Autowired
    private UserDtoValidator userDtoValidator;
    @Autowired
    private UserService userService;

    @GetMapping("/addItem")
    public String getAddItemPage(Model model) {
        // aici procesez din greu requestul, la final:.
        System.out.println("Rulez Get pe /addItem");
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);

        return "addItem";
    }

    @PostMapping("/addItem")
    public String postAddItemPage(ProductDto productDto, BindingResult bindingResult, @RequestParam("productImage") MultipartFile multipartFile) {
        System.out.println("Am primit: " + multipartFile);
        productDtoValidator.validate(productDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "addItem";
        }
        productService.add(productDto, multipartFile);
        return "redirect:/addItem"; // rulez redirect catre Get.

    }

    @GetMapping("/home")
    public String getHomePage(Model model) {
        List<ProductDto> productDtoList = productService.getAllProductDtos();
        model.addAttribute("products", productDtoList);
        return "home";
    }

    @GetMapping("/item/{itemId}")
    public String getViewItemPage(@PathVariable(value = "itemId") String itemId, Model model) {
        System.out.println("Am primit id-ul: " + itemId);
        Optional<ProductDto> optionalProductDto = productService.getProductDtoById(itemId);
        if(!optionalProductDto.isPresent()){
            return"errorPage";
        }
        ProductDto productDto = optionalProductDto.get();
        model.addAttribute("product", productDto);
        return "viewItem";

    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "registration";
    }
    @PostMapping(value="/registration")
    public String postRegistrationPage(Model model, UserDto userDto, BindingResult bindingResult){
    userDtoValidator.validate(userDto, bindingResult);
    if(bindingResult.hasErrors()){
        return "registration";
    }
    userService.add(userDto);
    return "redirect:/home";
    }
}
