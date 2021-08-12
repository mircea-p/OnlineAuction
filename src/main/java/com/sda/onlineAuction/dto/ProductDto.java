package com.sda.onlineAuction.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@ToString
@Getter
@Setter
public class ProductDto {

    private String id;
    private String name;
    private String description;
    private String startBidingPrice;
    private String category;
    private String endDateTime;
    private String image;


}
