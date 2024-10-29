package io.topfilms.api.controllers;

import io.topfilms.api.entities.MovieGenre;
import io.topfilms.api.services.moviegenre.MovieGenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class MovieGenreController {

    private static final Logger LOG = LoggerFactory.getLogger(MovieGenreController.class);

    @Autowired
    private MovieGenreService service;

    @GetMapping("/v1/movie-genres")
    public ResponseEntity<List<MovieGenre>> findAll(Authentication auth) {
        LOG.info("Find all movie genres requested by {}", auth.getName());
        return ResponseEntity.ok(service.findAll());
    }

}
