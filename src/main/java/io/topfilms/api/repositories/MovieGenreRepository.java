package io.topfilms.api.repositories;

import io.topfilms.api.entities.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieGenreRepository extends JpaRepository<MovieGenre, UUID> {
}
