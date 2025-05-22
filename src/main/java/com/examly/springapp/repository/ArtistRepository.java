package com.examly.springapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.web.bind.annotation.RequestParam;

import com.examly.springapp.model.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist,Long>{
    
    // @Query("SELECT a FROM Artist a WHERE a.email =: email")
    Optional<Artist> findByEmail(@RequestParam String email);
    
}