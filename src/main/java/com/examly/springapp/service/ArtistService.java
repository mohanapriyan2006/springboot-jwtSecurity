package com.examly.springapp.service;

import com.examly.springapp.repository.ArtistRepository;
import com.examly.springapp.model.Artist;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {
    @Autowired
    private ArtistRepository artistRepository;

    public Artist saveArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public Optional<Artist> getArtistById(Long id) {
        return artistRepository.findById(id);
    }

    public Optional<Artist> getArtistByEmail(String email) {
        return artistRepository.findByEmail(email);
    }

    public Artist updateArtist(Long id, Artist artist) {
        Optional<Artist> existingArtist = artistRepository.findById(id);
        if (existingArtist.isPresent()) {
            return artistRepository.save(artist);
        } else {
            return null;
        }
    }

    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }
}