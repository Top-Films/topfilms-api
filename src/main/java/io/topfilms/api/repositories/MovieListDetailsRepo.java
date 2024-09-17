package io.topfilms.api.repositories;

import io.topfilms.api.entities.MovieListDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovieListDetailsRepo extends JpaRepository<MovieListDetail, UUID> {
}
