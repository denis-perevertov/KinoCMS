package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    public BannersRepository bannerRepo;

    @Autowired
    public TheaterRepository theaterRepo;

    @Autowired
    public NewsRepository newsRepo;

    @Autowired
    private OffersRepository offerRepo;

    @GetMapping("/")
    public String showMainPage(Model model) {
        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());

        Iterable<Movie> movieList = movieRepo.findAll();
        model.addAttribute("movies", movieList);

        return "main_site/main_page";
    }

    @GetMapping("/posters")
    public String showPostersPage(Model model) {
        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());

        Iterable<Movie> movieList = movieRepo.findAll();
        model.addAttribute("movies", movieList);

        model.addAttribute("today", LocalDate.now());

        return "main_site/posters"; }

    @GetMapping("/schedule")
    public String showSchedulePage(Model model) {
        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());
        return "main_site/schedule"; }

    @GetMapping("/soon")
    public String showSoonPage(Model model) {
        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());

        Iterable<Movie> movieList = movieRepo.findAll();
        model.addAttribute("movies", movieList);

        model.addAttribute("today", LocalDate.now());

        return "main_site/soon"; }

    @GetMapping("/theaters_list")
    public String showTheatersPage(Model model) {
        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());

        Iterable<Movie_Theater> theaterList = theaterRepo.findAll();
        model.addAttribute("theaters", theaterList);

        return "main_site/theaters"; }

    @GetMapping("/offers_list")
    public String showOffersPage(Model model) {
        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());

        Iterable<Offer> offerList = offerRepo.findAll();
        model.addAttribute("offers", offerList);
        return "main_site/offers"; }

    @GetMapping("/about")
    public String showAboutPage(Model model) {
        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());

        return "main_site/about";
    }

    @GetMapping("/news-list")
    public String showNewsPage(Model model) {
        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());

        Iterable<News> newsList = newsRepo.findAll();
        model.addAttribute("news", newsList);

        return "main_site/news-list"; }

    @GetMapping("/ads")
    public String showAdsPage(Model model) {
        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());

        return "main_site/ads"; }

    @GetMapping("/cafe")
    public String showCafePage(Model model) {
        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());

        return "main_site/cafe"; }

    @GetMapping("/mobile")
    public String showMobilePage(Model model) {
        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());

        return "main_site/mobile"; }

    @GetMapping("/contacts")
    public String showContactsPage(Model model) {
        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());

        return "main_site/contacts"; }

    @GetMapping("/seance")
    public String showSeancePage(Model model) {
        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());

        return "main_site/seance"; }

    @GetMapping("/admin")
    public String admin(Model model) {
        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());
        return "admin/admin";
    }

    @GetMapping("/sms")
    public String sms(Model model) {
        return "sms/sms";
    }

    @GetMapping("/sms/users")
    public String chooseUsers(Model model) {

        Iterable<User> userList = userRepo.findAll();
        model.addAttribute("users", userList);

        return "sms" + "/users";
    }

}
