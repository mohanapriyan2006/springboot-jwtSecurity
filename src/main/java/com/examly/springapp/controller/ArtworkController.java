package com.examly.springapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import com.examly.springapp.model.Artwork;
import com.examly.springapp.service.ArtworkService;

@RestController
@RequestMapping("api/artworks")
public class ArtworkController {
    @Autowired
    private ArtworkService artworkService;

    @GetMapping("/test")
    public String test() {
        return "it worked ";
    }

    @GetMapping
    public ResponseEntity<Page<Artwork>> getAllArtworks(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "acs") String direction) {

        PageRequest pageable = PageRequest.of(page, size,
                Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy)));

        Page<Artwork> artworks = artworkService.getAllArtworks(pageable);
        return ResponseEntity.ok(artworks);
    }

    @GetMapping("/{id}")
    public Optional<Artwork> getArtworkById(@PathVariable Long id) {
        return artworkService.getArtworkById(id);
    }

    @PostMapping
    public ResponseEntity<Artwork> createArtwork(@RequestBody Artwork artwork) {
        Artwork artwork_body = artworkService.saveArtwork(artwork);
        return ResponseEntity.ok(artwork_body).status(HttpStatus.CREATED).body(artwork_body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artwork> updateArtwork(@PathVariable Long id, @RequestBody Artwork artwork) {
        Artwork updateArtwork = artworkService.updateArtwork(id, artwork);
        return (updateArtwork != null) ? ResponseEntity.ok(updateArtwork) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArtwork(@PathVariable Long id) {
        artworkService.deleteArtwork(id);
        return ResponseEntity.ok().body("{\"Message\": \"Artwork deleted successfully\"}");
    }
}