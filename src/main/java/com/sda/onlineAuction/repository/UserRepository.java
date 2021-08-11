package com.sda.onlineAuction.repository;

import com.sda.onlineAuction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
