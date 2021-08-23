package com.sda.onlineAuction.repository;

import com.sda.onlineAuction.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Integer> {

}
