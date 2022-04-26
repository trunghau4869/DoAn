package com.example.demo.repository.AuctionUserRepo;

import com.example.demo.model.AuctionUser;
import com.example.demo.model.AuctionUserKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionUserRepo extends JpaRepository<AuctionUser, AuctionUserKey> {
    List<AuctionUser> findByAuction_Product_IdProductOrderByStartingPriceDesc(int id);
}
