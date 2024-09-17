package io.topfilms.api.controllers;

import io.topfilms.api.entities.MovieListGenre;
import io.topfilms.api.services.movie_list_genre.MovieListGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class MovieListGenreController {

    @Autowired
    private MovieListGenreService genreService;

    @GetMapping
    public ResponseEntity<List<MovieListGenre>> getAll() {
        return ResponseEntity.ok(genreService.getAll());
    }

}
