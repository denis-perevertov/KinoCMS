package com.example.demo.controllers;

import com.example.demo.models.Banner;
import com.example.demo.models.Movie;
import com.example.demo.models.Movie_Theater;
import com.example.demo.repo.BannersRepository;
import com.example.demo.repo.MovieRepository;
import com.example.demo.repo.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/movies")
public class MoviesController {

    @Autowired
    private MovieRepository repo;

    @Autowired
    public BannersRepository bannerRepo;

    @Autowired
    public TheaterRepository theaterRepo;

    private static String dir_name = "movies";

    @GetMapping
    public String movies(Model model) {

        Iterable<Movie> movieList = repo.findAll();
        model.addAttribute("movies", movieList);

        return dir_name + "/movies";
    }

    @GetMapping("/{id}")
    public String showMoviePage(@PathVariable Long id,
                                Model model) {

        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());

        Iterable<Movie_Theater> theaterList = theaterRepo.findAll();
        model.addAttribute("theaters", theaterList);

        return "main_site/film_page";
    }

    @GetMapping("/{id}/edit")
    public String editMovie(@PathVariable Long id, Model model) {

        Movie movie = repo.findById(id).orElseThrow();

        model.addAttribute("movie", movie);

        return dir_name + "/movie-info";
    }

}
