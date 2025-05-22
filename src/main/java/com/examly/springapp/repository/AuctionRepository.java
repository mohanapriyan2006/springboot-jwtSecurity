package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Auction;


@Repository
public interface AuctionRepository extends JpaRepository<Auction,Long> {
        
}
