package com.sda.onlineAuction.controller;

import com.sda.onlineAuction.dto.BidDto;
import com.sda.onlineAuction.dto.ProductDto;
import com.sda.onlineAuction.dto.UserDto;
import com.sda.onlineAuction.model.Category;
import com.sda.onlineAuction.repository.ProductRepository;
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
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDtoValidator productDtoValidator;

    @Autowired
    private BidService bidService;
    @Autowired
    private BidDtoValidator bidDtoValidator;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/addItem")
    public String getAddItemPage(Model model) {
        // aici procesez din greu requestul, la final:.
        System.out.println("Rulez Get pe /addItem");
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);

        return "addItemX";
    }

    @GetMapping("/listOfProducts")
    public String getAllProductsList(Model model, ProductDto productDto, Authentication authentication) {
        System.out.println("Apelez pagina de: listOfProducts!");
        List<ProductDto> productDtoList = productService.getAllProductsDtos(authentication.getName());
        model.addAttribute("products", productDtoList);
        model.addAttribute("productDto", productDto);

        return "listOfProductsX";
    }

    @GetMapping("/listOfProducts1")
    public String getAllProductsListByCategory(Model model, ProductDto productDto, Authentication authentication) {
        System.out.println("Apelez pagina de: listOfProducts by Category!");
        Category category = Category.valueOf(productDto.getCategory());
        System.out.println("Ai ales categoria : " + category);
        List<ProductDto> productDtoList1 = productService.getAllActiveProductDtosByCategory(authentication.getName(), category);
        model.addAttribute("productDto", productDto);
        model.addAttribute("products", productDtoList1);
        return "listOfProductsX";
    }

    @GetMapping("/editItem/{id}")
    public String getEditProduct(@PathVariable(value = "id") String id, Model model,
                                 ProductDto productDto, Authentication authentication) {
        System.out.println("O intrat la get editItem");
        Optional<ProductDto> optionalProductDto = productService.getProductDtoById(id, authentication.getName());
        productDto = optionalProductDto.get();
        model.addAttribute("productDto", productDto);
        return "editItemX";
    }

    @PostMapping("/editItem")
    public String postEditProductPage(ProductDto productDto, BindingResult bindingResult, @RequestParam("productImage") MultipartFile multipartFile) {
        System.out.println("O intrat la post editItem");
//        System.out.println("Nume produs nou: "+ productDto.getName() +", id: "+ productDto.getId() );
        productService.update(productDto, multipartFile);
//        System.out.println("Nume produs nou: "+ productDto.getName() +", id: "+ productDto.getId() );
        return "redirect:/listOfProducts";
    }

    @GetMapping("/deleteItem/{id}")
    public String deleteProductPage(@PathVariable(value = "id") String id) {
        System.out.println("Am intrat la delete item!");
        productRepository.deleteById(Integer.valueOf(id));
        return "redirect:/listOfProducts";
    }

    @PostMapping("/addItem")
    public String postAddItemPage(ProductDto productDto, BindingResult bindingResult, @RequestParam("productImage") MultipartFile multipartFile) {
//        System.out.println("Am primit: " + multipartFile);
//        System.out.println("Am primit un : " + productDto.getName());
        productDtoValidator.validate(productDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "addItemX";
        }
        productService.add(productDto, multipartFile);
        return "redirect:/addItem"; // rulez redirect catre Get.

    }

    @GetMapping({"/home", "/"})
    public String getHomePage(Model model, Authentication authentication) {
        List<ProductDto> productDtoList = productService.getAllActiveProductDtos(authentication.getName());
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        model.addAttribute("products", productDtoList);
        return "homeX";
    }

    @GetMapping({"/home1"})
    public String getHome1Page(ProductDto productDto, Model model, Authentication authentication) {
        System.out.println("Categoria este: " + productDto.getCategory());
        Category category = Category.valueOf(productDto.getCategory());
        System.out.println("Ai ales categoria : " + category);
        List<ProductDto> productDtoList1 = productService.getAllActiveProductDtosByCategory(authentication.getName(), category);
//        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        model.addAttribute("products", productDtoList1);
        return "homeX";
    }


    @GetMapping("/myProducts")
    public String getmyProductsPage(Model model, Authentication authentication) {
        List<ProductDto> productDtoList = productService.getProductDtosFor(authentication.getName());
        model.addAttribute("products", productDtoList);
        return "myProductsX";
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
        return "viewItemX";

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
            return "viewItemX";
        }
        bidService.placeBid(bidDto, productId, authentication.getName());
        return "redirect:/item/" + productId;
    }

}
