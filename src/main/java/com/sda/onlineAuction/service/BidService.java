package com.sda.onlineAuction.service;

import com.sda.onlineAuction.dto.BidDto;
import com.sda.onlineAuction.mapper.BidMapper;
import com.sda.onlineAuction.model.Bid;
import com.sda.onlineAuction.model.Product;
import com.sda.onlineAuction.model.User;
import com.sda.onlineAuction.repository.BidRepository;
import com.sda.onlineAuction.repository.ProductRepository;
import com.sda.onlineAuction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.Optional;

@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private BidMapper bidMapper;

    public void placeBid(BidDto bidDto, String productId, String userEmail) {
//        System.out.println("Adaug valoarea: " + bidDto.getValue() + " pt produsul cu id-ul: " + productId
//                + " si utilizatorul cu adresa de mail: " + userEmail );

        Bid bid = bidMapper.map(bidDto, productId,userEmail);
        bidRepository.save(bid);
    }
}
