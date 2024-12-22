package com.satti.service;

import com.satti.entity.MovieDTO;
import com.satti.entity.MovieRow;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface MovieService {

    MovieDTO getMovieDetails(Integer id);

    List<MovieRow> searchMovies(String keyword);

    List<MovieRow> categorySearch(String[] tags, String[] languages);

//    String watchTogetherWithTitle(Integer id);
//
//    String commentWithTitle(Integer id, String comment);

    String getTitle(Integer id);

    List<MovieRow> getSuggestedMovies();

    List<MovieRow> getAllMovies();

    boolean watchTogetherWithTitle(Integer id, HttpSession session);

    boolean commentWithTitle(Integer id, String comment, HttpSession session);
}
