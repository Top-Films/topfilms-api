package io.topfilms.api.services.movie;

import io.topfilms.api.entities.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> findAll();

}
