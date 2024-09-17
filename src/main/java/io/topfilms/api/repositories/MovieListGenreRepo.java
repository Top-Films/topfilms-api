package io.topfilms.api.repositories;

import io.topfilms.api.entities.MovieListGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovieListGenreRepo extends JpaRepository<MovieListGenre, UUID>  {
}
