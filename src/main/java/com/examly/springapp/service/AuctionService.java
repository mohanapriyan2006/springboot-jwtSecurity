package com.examly.springapp.service;

import com.examly.springapp.repository.AuctionRepository;
import com.examly.springapp.model.Auction;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    public Auction saveAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    public Optional<Auction> getAuctionById(Long id) {
        return auctionRepository.findById(id);
    }

    public Auction updateAuction(Long id, Auction auction) {
        Optional<Auction> existingAuction = auctionRepository.findById(id);
        if (existingAuction.isPresent()) {
            Auction updatedAuction = existingAuction.get();
            updatedAuction.setTitle(auction.getTitle());
            updatedAuction.setStartDate(auction.getStartDate());
            updatedAuction.setEndDate(auction.getEndDate());
            updatedAuction.setStartingPrice(auction.getStartingPrice());
            return auctionRepository.save(updatedAuction);
        } else {
            return null;
        }
    }

    public void deleteAuction(Long id) {
        auctionRepository.deleteById(id);
    }
}