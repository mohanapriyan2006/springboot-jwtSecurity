package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Artwork;


@Repository
public interface ArtworkRepository extends JpaRepository<Artwork,Long> {

    
}
