package com.zy7y.watch_server.service;

import com.zy7y.watch_server.pojo.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getAll(String movieName, String movieYear);

    Movie insert(Movie movie);

    void update(Movie movie);

    void delete(Integer id);
}

