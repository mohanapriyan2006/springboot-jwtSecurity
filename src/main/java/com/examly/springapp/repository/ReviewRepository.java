package com.examly.springapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Review;




@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    
    Optional<Review> findByName(String name);
}
