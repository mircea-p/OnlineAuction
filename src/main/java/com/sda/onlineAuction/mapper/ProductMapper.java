package com.sda.onlineAuction.mapper;

import com.sda.onlineAuction.dto.ProductDto;
import com.sda.onlineAuction.model.Category;
import com.sda.onlineAuction.model.Product;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class ProductMapper {
    public Product map(ProductDto productDto, MultipartFile multipartFile){
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
        return product;

    }

    public ProductDto map(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId().toString());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory().name());
        productDto.setStartBidingPrice(product.getStartingPrice().toString());
        productDto.setEndDateTime(product.getEndDateTime().toString());

        String imageAsString = Base64.encodeBase64String(product.getImage());
        productDto.setImage(imageAsString);
        return productDto;
    }
}
