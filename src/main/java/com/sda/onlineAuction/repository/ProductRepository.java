package com.sda.onlineAuction.repository;

import com.sda.onlineAuction.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
