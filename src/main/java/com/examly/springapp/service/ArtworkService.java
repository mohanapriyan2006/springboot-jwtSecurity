package com.examly.springapp.service;

import com.examly.springapp.repository.ArtworkRepository;
import com.examly.springapp.model.Artwork;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ArtworkService {

    @Autowired
    private ArtworkRepository artworkRepository;

    public Artwork saveArtwork(Artwork artwork) {
        return artworkRepository.save(artwork);
    }

    public Page<Artwork> getAllArtworks(Pageable pageable) {

        if(pageable == null) {
            pageable = PageRequest.of(0,10);
        }
        return artworkRepository.findAll(pageable);
    }

    public Optional<Artwork> getArtworkById(Long id) {
        return artworkRepository.findById(id);
    }

    public Artwork updateArtwork(Long id, Artwork artwork) {
        Optional<Artwork> existingArtwork = artworkRepository.findById(id);
        if (existingArtwork.isPresent()) {
            Artwork updatedArtwork = existingArtwork.get();
            updatedArtwork.setTitle(artwork.getTitle());
            updatedArtwork.setDescription(artwork.getDescription());
            updatedArtwork.setPrice(artwork.getPrice());
            return artworkRepository.save(updatedArtwork);
        } else {
            return null;
        }
    }

    public void deleteArtwork(Long id) {
        artworkRepository.deleteById(id);
    }
}
