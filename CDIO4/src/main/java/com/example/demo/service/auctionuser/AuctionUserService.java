package com.example.demo.service.auctionuser;

import com.example.demo.model.AuctionUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuctionUserService {
    List<AuctionUser> findByProduct(int id);
    void create(AuctionUser auctionUser);
}
