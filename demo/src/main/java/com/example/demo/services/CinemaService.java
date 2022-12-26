package com.example.demo.services;

import com.example.demo.models.*;
import com.example.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class CinemaService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    public BannersRepository bannerRepo;

    @Autowired
    public TheaterRepository theaterRepo;

    @Autowired
    private OffersRepository offerRepo;

    @Autowired
    private NewsRepository newsRepo;

    @Autowired
    private PageRepository pagesRepo;


    public void insertImage(Model model) {

        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());

    }

    public void getMovies(Model model) {

        Iterable<Movie> movieList = movieRepo.findAll();
        model.addAttribute("movies", movieList);

    }

    public void getTheaters(Model model) {

        Iterable<Movie_Theater> theaterList = theaterRepo.findAll();
        model.addAttribute("theaters", theaterList);

    }

    public void getOffers(Model model) {

        Iterable<Offer> offerList = offerRepo.findAll();
        model.addAttribute("offers", offerList);

    }

    public void getUsers(Model model) {

        Iterable<User> userList = userRepo.findAll();
        model.addAttribute("users", userList);

    }

    public void getNews(Model model) {

        Iterable<News> newsList = newsRepo.findAll();
        model.addAttribute("news", newsList);

    }

    public void getPages(Model model) {

        Iterable<Page> pageList = pagesRepo.findAll();
        model.addAttribute("pages", pageList);

    }
}
