package com.zy7y.watch_server.dao;

import com.zy7y.watch_server.pojo.Movie;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MovieDao {

    List<Movie> getAll(Movie movie);

    Integer insert(Movie movie);

    void update(Movie movie);

    void delete(Integer id);

    Movie getMovieById(Integer id);

}
