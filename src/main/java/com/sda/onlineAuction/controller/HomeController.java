package com.sda.onlineAuction.controller;

import com.sda.onlineAuction.dto.BidDto;
import com.sda.onlineAuction.dto.ProductDto;
import com.sda.onlineAuction.dto.UserDto;
import com.sda.onlineAuction.service.BidService;
import com.sda.onlineAuction.service.ProductService;
import com.sda.onlineAuction.service.UserService;
import com.sda.onlineAuction.validator.BidDtoValidator;
import com.sda.onlineAuction.validator.ProductDtoValidator;
import com.sda.onlineAuction.validator.UserDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private BidService bidService;

    @Autowired
    private BidDtoValidator bidDtoValidator;

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

    @GetMapping({"/home","/"})
    public String getHomePage(Model model, Authentication authentication) {
        List<ProductDto> productDtoList = productService.getAllActiveProductDtos(authentication.getName());
        model.addAttribute("products", productDtoList);
        return "home";
    }
    @GetMapping("/myProducts")
    public String getmyProductsPage(Model model, Authentication authentication) {
        List<ProductDto> productDtoList = productService.getProductDtosFor(authentication.getName());
        model.addAttribute("products", productDtoList);
        return "myProducts";
    }

    @GetMapping("/item/{itemId}")
    public String getViewItemPage(@PathVariable(value = "itemId") String itemId,
                                  Model model, Authentication authentication) {
        System.out.println("Am primit id-ul: " + itemId);
        Optional<ProductDto> optionalProductDto = productService.getProductDtoById(itemId, authentication.getName());
        if (!optionalProductDto.isPresent()) {
            return "errorPage";
        }
        BidDto bidDto = new BidDto();
        model.addAttribute("bidDto", bidDto);
        ProductDto productDto = optionalProductDto.get();
        model.addAttribute("product", productDto);
        return "viewItem";

    }

    @PostMapping("item/{itemId}")
    public String postProductPage(BidDto bidDto, BindingResult bindingResult,
                                  @PathVariable(value = "itemId") String productId, Authentication authentication, Model model) {
        System.out.println("Am primit " + bidDto.getValue() + " pentru produsul cu id-ul: " + productId);

        bidDtoValidator.validate(bidDto, bindingResult, productId);
        if (bindingResult.hasErrors()) {
            Optional<ProductDto> optionalProductDto = productService.getProductDtoById(productId, authentication.getName());
            if (!optionalProductDto.isPresent()) {
                return "errorPage";
            }
            model.addAttribute("product", optionalProductDto.get());
            return "viewItem";
        }
        bidService.placeBid(bidDto, productId, authentication.getName());
        return "redirect:/item/" + productId;
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String postRegistrationPage(Model model, UserDto userDto, BindingResult bindingResult) {
        userDtoValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.add(userDto);
        return "redirect:/home";
    }

    @GetMapping(value = "/login")
    public String getLoginPage(Model model) {
        return "login";
    }
    @GetMapping(value = "/login-error")
    public String getLoginErrorPage(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
