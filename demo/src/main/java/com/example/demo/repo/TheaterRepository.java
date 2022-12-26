package com.example.demo.repo;

import com.example.demo.models.Movie_Theater;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends CrudRepository<Movie_Theater, Long> {
}
