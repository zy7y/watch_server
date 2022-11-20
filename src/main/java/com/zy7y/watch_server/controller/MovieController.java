package com.zy7y.watch_server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zy7y.watch_server.pojo.Movie;
import com.zy7y.watch_server.pojo.rep.R;
import com.zy7y.watch_server.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="电影相关")
@RestController
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Operation(summary = "获取电影列表", security = {@SecurityRequirement(name="token")})
    @GetMapping("/movie")
    public R getAll(@RequestParam(required = false) String movieName,
                    @RequestParam(required = false) String movieYear,
                    @RequestParam(defaultValue = "1") Integer pageNum,
                    @RequestParam(defaultValue = "10") Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Movie> movieList = movieService.getAll(movieName, movieYear);
        PageInfo<Movie> pageInfo = new PageInfo<>(movieList);
        return R.success(pageInfo);
    }

    @Operation(summary = "新增电影", security = {@SecurityRequirement(name="token")})
    @PostMapping("/movie")
    public R addMovie(@RequestBody Movie movie){
        Movie movieObj = movieService.insert(movie);
        return R.success(movieObj);
    }

    @Operation(summary = "修改电影", security = {@SecurityRequirement(name="token")})
    @PutMapping("/movie")
    public R updateMovie(@RequestBody Movie movie){
        movieService.update(movie);
        return R.success();
    }

    @Operation(summary = "删除电影", security = {@SecurityRequirement(name="token")})
    @DeleteMapping("/movie/{id}")
    public R deleteMovie(@PathVariable Integer id){
        movieService.delete(id);
        return R.success();
    }
}
