package com.zy7y.watch_server.service.impl;

import com.zy7y.watch_server.dao.MovieDao;
import com.zy7y.watch_server.pojo.Movie;
import com.zy7y.watch_server.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;

    @Override
    public List<Movie> getAll(String movieName, String movieYear) {
        Movie movie = new Movie();
        if (movieName instanceof String && !movieName.trim().equals("")){
            movie.setMovieName("%" + movieName + "%");
        }
        movie.setMovieYear(movieYear);
        return movieDao.getAll(movie);
    }

    @Override
    public Movie insert(Movie movie) {
        movieDao.insert(movie);
        return movieDao.getMovieById(movie.getId());
    }

    @Override
    public void update(Movie movie) {
        movieDao.update(movie);
    }

    @Override
    public void delete(Integer id) {
        movieDao.delete(id);
    }
}
