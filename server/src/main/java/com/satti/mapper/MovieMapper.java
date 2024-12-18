package com.satti.mapper;

import com.satti.entity.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieMapper extends MongoRepository<Movie, String> {
    Movie getMovieById(Integer id);
}
