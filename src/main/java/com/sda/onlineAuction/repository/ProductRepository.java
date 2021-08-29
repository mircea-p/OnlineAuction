package com.sda.onlineAuction.repository;

import com.sda.onlineAuction.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByEndDateTimeAfter(LocalDateTime localDateTime);
}
