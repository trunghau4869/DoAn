package com.example.demo.repository.AuctionUserRepo;

import com.example.demo.model.Auction;
import com.example.demo.model.AuctionUser;
import com.example.demo.model.AuctionUserKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepo extends JpaRepository<Auction, Integer> {

    Auction findByProduct_IdProduct(Integer idSP);
}
