package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repo.*;
import com.example.demo.services.BannerService;
import com.example.demo.services.CinemaService;
import com.example.demo.services.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@Slf4j
@SuppressWarnings("unused")
public class MainController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    public BannersRepository bannerRepo;

    @Autowired
    public BannersBGRepository bgBannerRepo;

    @Autowired
    public BannersOFFRepository offBannerRepo;

    @Autowired
    public TheaterRepository theaterRepo;

    @Autowired
    public PageRepository pageRepo;

    @Autowired
    public NewsRepository newsRepo;

    @Autowired
    private OffersRepository offerRepo;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private CinemaService cinemaService;

    @GetMapping("/")
    public String showMainPage(UriComponentsBuilder ucb, Model model) {

        log.info(ucb.toUriString() + " - Подгружаем задний фон, скорость вращения карусели...");

        model.addAttribute("speed1", BannersController.turn_speed_1);
        model.addAttribute("speed2", BannersController.turn_speed_2);

        bannerService.setBackground(model);

        log.info(ucb.toUriString() + " - Загружаем списки фильмов и баннеров, сегодняшнюю дату...");

        cinemaService.getMovies(model);
        bannerService.insertBannerLists(model);

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");

        return "main_site/main_page";
    }

    @GetMapping("/posters")
    public String showPostersPage(UriComponentsBuilder ucb, Model model) {

        bannerService.setBackground(model);
        log.info(ucb.toUriString() + " - Загружен задний фон");

        cinemaService.getMovies(model);
        log.info(ucb.toUriString() + " - Загружен список фильмов + сегодняшняя дата");

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");
        return "main_site/posters"; }

    @GetMapping("/schedule")
    public String showSchedulePage(UriComponentsBuilder ucb, Model model) {

        bannerService.setBackground(model);
        log.info(ucb.toUriString() + " - Загружен задний фон");

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");
        return "main_site/schedule"; }

    @GetMapping("/soon")
    public String showSoonPage(UriComponentsBuilder ucb, Model model) {
        
        bannerService.setBackground(model);
        log.info(ucb.toUriString() + " - Загружен задний фон");

        cinemaService.getMovies(model);
        log.info(ucb.toUriString() + " - Загружен список фильмов + сегодняшняя дата");

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");
        return "main_site/soon"; }

    @GetMapping("/theaters_list")
    public String showTheatersPage(UriComponentsBuilder ucb, Model model) {

        bannerService.setBackground(model);
        log.info(ucb.toUriString() + " - Загружен задний фон");

        cinemaService.getTheaters(model);
        log.info(ucb.toUriString() + " - Загружен список кинотеатров");

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");
        return "main_site/theaters"; }

    @GetMapping("/offers_list")
    public String showOffersPage(UriComponentsBuilder ucb, Model model) {

        bannerService.setBackground(model);
        log.info(ucb.toUriString() + " - Загружен задний фон");

        cinemaService.getOffers(model);
        log.info(ucb.toUriString() + " - Загружен список акций");

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");
        return "main_site/offers"; }

    @GetMapping("/about")
    public String showAboutPage(UriComponentsBuilder ucb, Model model) {

        bannerService.setBackground(model);
        log.info(ucb.toUriString() + " - Загружен задний фон");

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");
        return "main_site/about";
    }

    @GetMapping("/news-list")
    public String showNewsPage(UriComponentsBuilder ucb, Model model) {

        bannerService.setBackground(model);
        log.info(ucb.toUriString() + " - Загружен задний фон");

        cinemaService.getNews(model);
        log.info(ucb.toUriString() + " - Загружен список новостей");

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");
        return "main_site/news-list"; }

    @GetMapping("/ads")
    public String showAdsPage(UriComponentsBuilder ucb, Model model) {

        bannerService.setBackground(model);
        log.info(ucb.toUriString() + " - Загружен задний фон");

        Page page = pageRepo.findById(5L).orElseThrow();
        model.addAttribute("page", page);

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");
        return "main_site/ads"; }

    @GetMapping("/cafe")
    public String showCafePage(UriComponentsBuilder ucb, Model model) {

        bannerService.setBackground(model);
        log.info(ucb.toUriString() + " - Загружен задний фон");

        Page page = pageRepo.findById(3L).orElseThrow();
        model.addAttribute("page", page);

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");
        return "main_site/cafe"; }

    @GetMapping("/mobile")
    public String showMobilePage(UriComponentsBuilder ucb, Model model) {

        bannerService.setBackground(model);
        log.info(ucb.toUriString() + " - Загружен задний фон");

        Page page = pageRepo.findById(6L).orElseThrow();
        model.addAttribute("page", page);

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");
        return "main_site/mobile"; }

    @GetMapping("/contacts")
    public String showContactsPage(UriComponentsBuilder ucb, Model model) {

        bannerService.setBackground(model);
        log.info(ucb.toUriString() + " - Загружен задний фон");

        Page page = pageRepo.findById(7L).orElseThrow();
        model.addAttribute("page", page);

        Iterable<Movie_Theater> theaterList = theaterRepo.findAll();
        model.addAttribute("theaters", theaterList);

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");
        return "main_site/contacts"; }

    @GetMapping("/seance/{id}")
    public String showSeancePage(@PathVariable Long id, UriComponentsBuilder ucb, Model model) {

        bannerService.setBackground(model);
        log.info(ucb.toUriString() + " - Загружен задний фон");

        model.addAttribute("movie", movieRepo.findById(id).orElseThrow());

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");
        return "main_site/seance"; }

    @GetMapping("/seance")
    public String showTemplateSeancePage(UriComponentsBuilder ucb, Model model) {

        bannerService.setBackground(model);
        log.info(ucb.toUriString() + " - Загружен задний фон");

        model.addAttribute("movie", movieRepo.findById(7L).orElseThrow());

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");
        return "main_site/seance"; }

    @GetMapping("/admin")
    public String admin(UriComponentsBuilder ucb, Model model) {

        Iterable<User> userList = userRepo.findAll();
        model.addAttribute("userList", userList);

        Iterable<Movie> moviesList = movieRepo.findAll();
        model.addAttribute("movieList", moviesList);

        Iterable<News> newsList = newsRepo.findAll();
        model.addAttribute("news", newsList);

        Iterable<Offer> offersList = offerRepo.findAll();
        model.addAttribute("offers", offersList);

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");
        return "admin/admin";
    }

    @GetMapping("/sms")
    public String sms(UriComponentsBuilder ucb, Model model) {

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");
        return "sms/sms";
    }

    @GetMapping("/sms/users")
    public String chooseUsers(UriComponentsBuilder ucb, Model model) {

        Iterable<User> userList = userRepo.findAll();
        model.addAttribute("users", userList);

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");
        return "sms" + "/users";
    }

    @PostMapping("/sms")
    public @ResponseBody String sendEmail(UriComponentsBuilder ucb, Model model) {

        try {
			emailSenderService.sendMail();
		} catch (Exception e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		}

        log.info(ucb.toUriString() + " - Отображаем HTML-страницу!");
        return "Email sent successfully";
    }

    @PostMapping("/sms/users")
    public String returnToSms(Model model) {
        return "sms/sms";
    }
}
