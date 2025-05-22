package com.examly.springapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Artist;
import com.examly.springapp.service.ArtistService;

@RestController
@RequestMapping("api/artists")
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    @GetMapping("/test")
    public String test() {
        return "it worked ";
    }

    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    @GetMapping("/emails")
    public Optional<Artist> getArtistByEmail(@RequestParam String email) {
        return artistService.getArtistByEmail(email);
    }

    @PostMapping
    public ResponseEntity<Artist> saveArtist(@RequestBody Artist artist) {
        Artist artist_body = artistService.saveArtist(artist);
        return ResponseEntity.status(HttpStatus.CREATED).body(artist_body);
    }

    @GetMapping("/{id}")
    public Optional<Artist> getArtistById(@PathVariable Long id) {
        return artistService.getArtistById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable Long id, @RequestBody Artist artist) {
        Artist updateArtist = artistService.updateArtist(id, artist);
        return (updateArtist != null) ? ResponseEntity.ok(updateArtist) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.ok().body("{\"Message\": \"Artist deleted successfully\"}");
    }
}