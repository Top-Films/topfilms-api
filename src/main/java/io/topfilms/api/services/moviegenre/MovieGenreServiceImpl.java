package io.topfilms.api.services.moviegenre;

import io.topfilms.api.common.SecurityUtil;
import io.topfilms.api.entities.MovieGenre;
import io.topfilms.api.repositories.MovieGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieGenreServiceImpl implements MovieGenreService {

    @Autowired
    private MovieGenreRepository repository;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public List<MovieGenre> findAll() {
        return repository.findAll();
    }

}
