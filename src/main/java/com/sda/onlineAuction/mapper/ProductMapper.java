package com.sda.onlineAuction.mapper;

import com.sda.onlineAuction.dto.ProductDto;
import com.sda.onlineAuction.model.Bid;
import com.sda.onlineAuction.model.Category;
import com.sda.onlineAuction.model.Product;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public ProductDto map(Product product, String email){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId().toString());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory().name());
        productDto.setStartBidingPrice(product.getStartingPrice().toString());
        productDto.setEndDateTime(product.getEndDateTime().toString());

        Integer max = getBidMaxValue(product.getBidList());
        productDto.setCurrentBidPrice(max.toString());

        Integer loggedUserMaxValue = getLoggedUserBiggestBid(product, email);
        productDto.setLoggedUserMaxBid(loggedUserMaxValue.toString());

        String imageAsString = Base64.encodeBase64String(product.getImage());
        productDto.setImage(imageAsString);
        return productDto;
    }

    private Integer getLoggedUserBiggestBid(Product product, String email) {

        List<Bid> bidList = product.getBidList();
        List<Bid> loggedUserBidList = new ArrayList<>();
        for(Bid bid: bidList){
            if(bid.getUser().getEmail().equals(email)){
                loggedUserBidList.add(bid);
            }
        }
        Integer max = getBidMaxValue(loggedUserBidList);
        return max;
    }

    private Integer getBidMaxValue(List<Bid> bidList) {
        Integer max=0;
        for(Bid bid: bidList){
            if(max< bid.getValue()){
                max= bid.getValue();
            }
        }
        return max;
    }
}
