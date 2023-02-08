package com.example.demo.controllers;

import com.example.demo.models.Banner;
import com.example.demo.models.Movie;
import com.example.demo.models.Movie_Theater;
import com.example.demo.models.News;
import com.example.demo.repo.BannersRepository;
import com.example.demo.repo.MovieRepository;
import com.example.demo.repo.TheaterRepository;
import com.example.demo.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    public static String image_upload_dir = "demo/" + "src/main/resources/static/images/" + dir_name;

    @GetMapping
    public String movies(Model model) {

        Iterable<Movie> movieList = repo.findAll();

        List<Movie> sortedList = new ArrayList<>();
        movieList.forEach(sortedList::add);
        sortedList.sort(Comparator.comparing(Movie::getName));

        model.addAttribute("movies", sortedList);
        model.addAttribute("today", LocalDate.now());

        return dir_name + "/movies";
    }

    @GetMapping("/{id}")
    public String showMoviePage(@PathVariable Long id,
                                Model model) {

        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());

        Iterable<Movie_Theater> theaterList = theaterRepo.findAll();
        model.addAttribute("theaters", theaterList);

        Movie movie = repo.findById(id).orElseThrow();
        model.addAttribute("movie", movie);
        model.addAttribute("url", movie.getYoutube_url());

        return "main_site/film_page";
    }

    @GetMapping("/{id}/edit")
    public String editMovie(@PathVariable Long id, Model model) {

        Movie movie = repo.findById(id).orElseThrow();

        model.addAttribute("movie", movie);

        return dir_name + "/movie-info";
    }

    @PostMapping("{id}/edit")
    public String newsEditSubmit(@PathVariable Long id,
                                 @RequestParam String name,
                                 @RequestParam String description,
                                 @RequestParam(required = false) MultipartFile main_picture,
                                 @RequestParam(required = false) MultipartFile picture1,
                                 @RequestParam(required = false) MultipartFile picture2,
                                 @RequestParam(required = false) MultipartFile picture3,
                                 @RequestParam(required = false) MultipartFile picture4,
                                 @RequestParam(required = false) MultipartFile picture5,
                                 Model model) throws IOException, ParseException, URISyntaxException {

        Movie movie = repo.findById(id).orElseThrow();
        List<MultipartFile> pictureList = new ArrayList<>(List.of(main_picture, picture1, picture2, picture3, picture4, picture5));

        movie.setName(name);
        movie.setDescription(description);

        String fileName = main_picture.getOriginalFilename();

        if(!main_picture.isEmpty() && main_picture.getOriginalFilename() != null) movie.setMain_picture(fileName);
        if(!picture1.isEmpty() && picture1.getOriginalFilename() != null) movie.setPicture1(picture1.getOriginalFilename());
        if(!picture2.isEmpty() && picture2.getOriginalFilename() != null) movie.setPicture2(picture2.getOriginalFilename());
        if(!picture3.isEmpty() && picture3.getOriginalFilename() != null) movie.setPicture3(picture3.getOriginalFilename());
        if(!picture4.isEmpty() && picture4.getOriginalFilename() != null) movie.setPicture4(picture4.getOriginalFilename());
        if(!picture5.isEmpty() && picture5.getOriginalFilename() != null) movie.setPicture5(picture5.getOriginalFilename());

        repo.save(movie);

        String uploadDir = image_upload_dir;

        for(MultipartFile picture : pictureList) {
            String f = StringUtils.cleanPath(picture.getOriginalFilename());
            if(picture == null) continue;
            FileUploadUtil.saveFile(uploadDir, f, picture);
        }

        return "redirect:/movies";
    }

}
