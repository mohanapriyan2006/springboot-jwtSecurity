package com.examly.springapp.service;

import com.examly.springapp.repository.ReviewRepository;
import com.examly.springapp.model.Review;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Review saveReview(Review review) {
        review.setPassword(passwordEncoder.encode(review.getPassword()));
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public Review updateReview(Long id, Review review) {
        Optional<Review> existingReview = reviewRepository.findById(id);
        if (existingReview.isPresent()) {
            Review updatedReview = existingReview.get();
            updatedReview.setName(review.getName());
            updatedReview.setRole(review.getRole());
            updatedReview.setPassword(passwordEncoder.encode(review.getPassword()));
            return reviewRepository.save(updatedReview);
        } else {
            return null;
        }
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}