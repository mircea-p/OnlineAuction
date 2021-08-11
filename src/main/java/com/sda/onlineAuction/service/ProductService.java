package com.sda.onlineAuction.service;

import com.sda.onlineAuction.dto.ProductDto;
import com.sda.onlineAuction.model.Category;
import com.sda.onlineAuction.model.Product;
import com.sda.onlineAuction.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void add(ProductDto productDto, MultipartFile multipartFile){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(Category.valueOf(productDto.getCategory()));
        product.setStartingPrice(Integer.valueOf(productDto.getStartBidingPrice()));
        product.setEndDateTime(LocalDateTime.parse(productDto.getEndDateTime()));
        try {
            product.setImage(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        productRepository.save(product);
    }

    public List<ProductDto> getAllProductDtos() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> result = new ArrayList<>();
        for(Product product: products){
            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setCategory(product.getCategory().name());
            productDto.setStartBidingPrice(product.getStartingPrice().toString());
            productDto.setEndDateTime(product.getEndDateTime().toString());
            result.add(productDto);
        }
        return result;
    }
}
