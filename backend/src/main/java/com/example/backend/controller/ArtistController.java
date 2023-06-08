package com.example.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend.entity.Artist;

@RestController
public class ArtistController {


    // localhost:8080/artist
    @GetMapping("artist")
    public Artist getArtist(){
        Artist artist = new Artist(
                1,
                "Daniel",
                "Shin"
        );
        return artist;
    }
}
