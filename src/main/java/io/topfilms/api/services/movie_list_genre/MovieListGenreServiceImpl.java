package io.topfilms.api.services.movie_list_genre;

import io.topfilms.api.entities.MovieListGenre;
import io.topfilms.api.repositories.MovieListGenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieListGenreServiceImpl implements MovieListGenreService {

    @Autowired
    private MovieListGenreRepo repo;

    @Override
    public List<MovieListGenre> getAll() {
        return repo.findAll();
    }

}
