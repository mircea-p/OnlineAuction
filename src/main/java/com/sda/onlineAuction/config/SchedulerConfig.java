package com.sda.onlineAuction.config;

import com.sda.onlineAuction.model.Bid;
import com.sda.onlineAuction.model.Product;
import com.sda.onlineAuction.model.User;
import com.sda.onlineAuction.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private ProductRepository productRepository;

    @Scheduled(fixedDelay = 5000)
    public void cronJob(){

   //     System.out.println("running cron job at: "+ LocalDateTime.now());
        List<Product> productList = productRepository.findAllExpiredWithoutWinnersAssigned(LocalDateTime.now());
   //     System.out.println("We found " + productList.size()+" products eligible for winner assigned");
        for(Product product : productList){
            Integer max = 0;
            User winner = null;
            for(Bid bid : product.getBidList()){
                if(max < bid.getValue()){
                    max = bid.getValue();
                    winner = bid.getUser();
                }
            }
            product.setWinner(winner);
            productRepository.save(product);
        }

    }



}
