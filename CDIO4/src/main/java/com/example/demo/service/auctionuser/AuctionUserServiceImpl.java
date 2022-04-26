package com.example.demo.service.auctionuser;

import com.example.demo.model.AuctionUser;
import com.example.demo.repository.AuctionUserRepo.AuctionUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionUserServiceImpl implements AuctionUserService{
    @Autowired
    AuctionUserRepo auctionUserRepo;

    @Override
    public List<AuctionUser> findByProduct(int id) {
        return auctionUserRepo.findByAuction_Product_IdProductOrderByStartingPriceDesc(id);
    }

    @Override
    public void create(AuctionUser auctionUser) {
        auctionUserRepo.save(auctionUser);
    }
}
