package com.example.demo.controllers;


import com.example.demo.models.Banner;
import com.example.demo.models.Hall;
import com.example.demo.models.HallId;
import com.example.demo.models.Movie_Theater;
import com.example.demo.repo.BannersRepository;
import com.example.demo.repo.HallsRepository;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/theaters")
@SuppressWarnings("unused")
public class TheaterController {



    @Autowired
    public TheaterRepository repo;

    @Autowired
    public HallsRepository hallRepo;

    @Autowired
    public BannersRepository bannerRepo;

    private static String dir_name = "theaters";
    public static String image_upload_dir = "demo/" + "src/main/resources/static/images/" + dir_name;


    @GetMapping
    public String theaters(Model model) {

        Iterable<Movie_Theater> theaterList = repo.findAll();
        model.addAttribute("theaters", theaterList);

        return dir_name + "/theaters";
    }

    @GetMapping("/{id}/edit")
    public String EditTheater(@PathVariable Long id,
                              Model model) {

        Movie_Theater theaterToEdit = repo.findById(id).orElseThrow();
        model.addAttribute("theater", theaterToEdit);

        return dir_name + "/theater-edit";
    }

    @GetMapping("/add")
    public String addTheater(@ModelAttribute Movie_Theater theater) {
        return dir_name + "/theater-add";
    }

    @GetMapping("/{id}/edit/add-hall")
    public String AddHallForTheater(@PathVariable Long id,
                                    @ModelAttribute Movie_Theater theater,
                                    Model model) {

        model.addAttribute("theater", theater);
        model.addAttribute("hall", new Hall());

        return dir_name + "/add-hall";
    }

    @GetMapping("/{id}/edit-hall/{hall_id}")
    public String editHallForTheater(@PathVariable Long id,
                                    @PathVariable Long hall_id,
                                    Model model) {

        model.addAttribute("theater", repo.findById(id).orElseThrow());
        model.addAttribute("hall", hallRepo.findById(new HallId(hall_id, id)).orElseThrow());

        return dir_name + "/edit-hall";
    }

    @GetMapping("/{id}/delete-hall/{hall_id}")
    public String deleteHallForTheater(@PathVariable Long id,
                                     @PathVariable Long hall_id,
                                     @ModelAttribute Movie_Theater theater,
                                     Model model) {

        model.addAttribute("theater", theater);
        model.addAttribute("hall", hallRepo.findById(new HallId(hall_id, id)).orElseThrow());

        theater.getHalls().remove(hallRepo.findById(new HallId(hall_id, id)).orElseThrow());
        hallRepo.delete(hallRepo.findById(new HallId(hall_id, id)).orElseThrow());

        return "redirect:/" + dir_name + "/" + id + "/edit";
    }

    //СДЕЛАТь
    @PostMapping("/{id}/edit")
    public String EditTheaterSubmit(@PathVariable Long id,
                                    @RequestParam String name,
                                    @RequestParam String description,
                                    @RequestParam String conditions,
                                    @RequestParam(required = false) MultipartFile logo,
                                    @RequestParam(required = false) MultipartFile banner_picture,
                                    @RequestParam(required = false) MultipartFile picture1,
                                    @RequestParam(required = false) MultipartFile picture2,
                                    @RequestParam(required = false) MultipartFile picture3,
                                    @RequestParam(required = false) MultipartFile picture4,
                                    @RequestParam(required = false) MultipartFile picture5,
                                    Model model) throws IOException, URISyntaxException {

        Movie_Theater theater = repo.findById(id).orElseThrow();
        List<MultipartFile> pictureList = new ArrayList<>(List.of(logo, banner_picture, picture1, picture2, picture3, picture4, picture5));

        theater.setName(name);
        theater.setDescription(description);
        theater.setConditions(conditions);

        if(!logo.isEmpty()) theater.setLogo(logo.getOriginalFilename());
        if(!banner_picture.isEmpty()) theater.setBanner_picture(banner_picture.getOriginalFilename());
        if(!picture1.isEmpty()) theater.setPicture1(picture1.getOriginalFilename());
        if(!picture2.isEmpty()) theater.setPicture2(picture2.getOriginalFilename());
        if(!picture3.isEmpty()) theater.setPicture3(picture3.getOriginalFilename());
        if(!picture4.isEmpty()) theater.setPicture4(picture4.getOriginalFilename());
        if(!picture5.isEmpty()) theater.setPicture5(picture5.getOriginalFilename());

        Movie_Theater saved_theater = repo.save(theater);

        String uploadDir = image_upload_dir + "/" + saved_theater.getTheater_id();

        for(MultipartFile picture : pictureList) {
            String f = StringUtils.cleanPath(picture.getOriginalFilename());
            if(picture == null) continue;
            FileUploadUtil.saveFile(uploadDir, f, picture);
        }


        return "redirect:/theaters";
    }

    @PostMapping("/{id}/edit/add-hall")
    public String AddHallForTheaterSubmit(@PathVariable Long id,
                                          @RequestParam String name,
                                          @RequestParam String description,
                                          @RequestParam(required = false) MultipartFile hall_scheme,
                                          @RequestParam(required = false) MultipartFile banner_picture,
                                          @RequestParam(required = false) MultipartFile picture1,
                                          @RequestParam(required = false) MultipartFile picture2,
                                          @RequestParam(required = false) MultipartFile picture3,
                                          @RequestParam(required = false) MultipartFile picture4,
                                          @RequestParam(required = false) MultipartFile picture5,
                                          Model model) throws IOException, URISyntaxException {

        Movie_Theater theater = repo.findById(id).orElseThrow();
        List<Hall> hallList = theater.getHalls();

        Hall hall = new Hall();
        Long hall_number = Long.valueOf(hallList.size() + 1);
        hall.setHallId(new HallId(hall_number, theater.getTheater_id()));

        List<MultipartFile> pictureList = new ArrayList<>(List.of(hall_scheme, banner_picture,
                picture1, picture2, picture3, picture4, picture5));

        hall.setName(name);
        hall.setDescription(description);

        if(!hall_scheme.isEmpty() && hall_scheme.getOriginalFilename() != null) hall.setHall_scheme(hall_scheme.getOriginalFilename());
        if(!banner_picture.isEmpty() && banner_picture.getOriginalFilename() != null) hall.setBanner_picture(banner_picture.getOriginalFilename());
        if(!picture1.isEmpty() && picture1.getOriginalFilename() != null) hall.setPicture1(picture1.getOriginalFilename());
        if(!picture2.isEmpty() && picture2.getOriginalFilename() != null) hall.setPicture2(picture2.getOriginalFilename());
        if(!picture3.isEmpty() && picture3.getOriginalFilename() != null) hall.setPicture3(picture3.getOriginalFilename());
        if(!picture4.isEmpty() && picture4.getOriginalFilename() != null) hall.setPicture4(picture4.getOriginalFilename());
        if(!picture5.isEmpty() && picture5.getOriginalFilename() != null) hall.setPicture5(picture5.getOriginalFilename());

        hall.setCreation_date(LocalDate.now());

        hallRepo.save(hall);

        theater.getHalls().add(hall);

        repo.save(theater);

        String uploadDir = image_upload_dir + "/" + id + "/" + name;

        for(MultipartFile picture : pictureList) {
            String f = StringUtils.cleanPath(Objects.requireNonNull(picture.getOriginalFilename()));
            if(picture.isEmpty()) continue;
            FileUploadUtil.saveFile(uploadDir, f, picture);
        }

        return "redirect:/theaters/" + id + "/edit";
    }

    @PostMapping("/{id}/edit-hall/{hall_id}")
    public String editHallForTheaterSubmit(@PathVariable Long id,
                                           @PathVariable Long hall_id,
                                           @RequestParam String name,
                                           @RequestParam String description,
                                           @RequestParam(required = false) MultipartFile hall_scheme,
                                           @RequestParam(required = false) MultipartFile banner_picture,
                                           @RequestParam(required = false) MultipartFile picture1,
                                           @RequestParam(required = false) MultipartFile picture2,
                                           @RequestParam(required = false) MultipartFile picture3,
                                           @RequestParam(required = false) MultipartFile picture4,
                                           @RequestParam(required = false) MultipartFile picture5,
                                           Model model) throws IOException, URISyntaxException {

        Movie_Theater theater = repo.findById(id).orElseThrow();
        Hall hall = hallRepo.findById(new HallId(hall_id, id)).orElseThrow();

        List<MultipartFile> pictureList = new ArrayList<>(List.of(hall_scheme, banner_picture,
                picture1, picture2, picture3, picture4, picture5));

        hall.setName(name);
        hall.setDescription(description);

        if(!hall_scheme.isEmpty() && hall_scheme.getOriginalFilename() != null) hall.setHall_scheme(hall_scheme.getOriginalFilename());
        if(!banner_picture.isEmpty() && banner_picture.getOriginalFilename() != null) hall.setBanner_picture(banner_picture.getOriginalFilename());
        if(!picture1.isEmpty() && picture1.getOriginalFilename() != null) hall.setPicture1(picture1.getOriginalFilename());
        if(!picture2.isEmpty() && picture2.getOriginalFilename() != null) hall.setPicture2(picture2.getOriginalFilename());
        if(!picture3.isEmpty() && picture3.getOriginalFilename() != null) hall.setPicture3(picture3.getOriginalFilename());
        if(!picture4.isEmpty() && picture4.getOriginalFilename() != null) hall.setPicture4(picture4.getOriginalFilename());
        if(!picture5.isEmpty() && picture5.getOriginalFilename() != null) hall.setPicture5(picture5.getOriginalFilename());

        hallRepo.save(hall);

        String uploadDir = image_upload_dir + "/" + id + "/" + name;

        for(MultipartFile picture : pictureList) {
            String f = StringUtils.cleanPath(Objects.requireNonNull(picture.getOriginalFilename()));
            if(picture.isEmpty()) continue;
            FileUploadUtil.saveFile(uploadDir, f, picture);
        }

        return "redirect:/theaters/" + id + "/edit";
    }

    @PostMapping("/add")
    public String addTheaterSubmit( @RequestParam String name,
                                    @RequestParam String description,
                                    @RequestParam String conditions,
                                    @RequestParam(required = false) MultipartFile logo,
                                    @RequestParam(required = false) MultipartFile banner_picture,
                                    @RequestParam(required = false) MultipartFile picture1,
                                    @RequestParam(required = false) MultipartFile picture2,
                                    @RequestParam(required = false) MultipartFile picture3,
                                    @RequestParam(required = false) MultipartFile picture4,
                                    @RequestParam(required = false) MultipartFile picture5,
                                    Model model) throws IOException, URISyntaxException {

        Movie_Theater theater = new Movie_Theater();
        List<MultipartFile> pictureList = new ArrayList<>(List.of(logo, banner_picture, picture1, picture2, picture3, picture4, picture5));

        theater.setName(name);
        theater.setDescription(description);
        theater.setConditions(conditions);

        if(!logo.isEmpty()) theater.setLogo(logo.getOriginalFilename());
        if(!banner_picture.isEmpty()) theater.setBanner_picture(banner_picture.getOriginalFilename());
        if(!picture1.isEmpty()) theater.setPicture1(picture1.getOriginalFilename());
        if(!picture2.isEmpty()) theater.setPicture2(picture2.getOriginalFilename());
        if(!picture3.isEmpty()) theater.setPicture3(picture3.getOriginalFilename());
        if(!picture4.isEmpty()) theater.setPicture4(picture4.getOriginalFilename());
        if(!picture5.isEmpty()) theater.setPicture5(picture5.getOriginalFilename());

        Movie_Theater saved_theater = repo.save(theater);

        String uploadDir = image_upload_dir + "/" + saved_theater.getTheater_id();

        for(MultipartFile picture : pictureList) {
            String f = StringUtils.cleanPath(picture.getOriginalFilename());
            if(picture == null) continue;
            FileUploadUtil.saveFile(uploadDir, f, picture);
        }


        return "redirect:/theaters";
    }

    //Показ инфы о кинотеатре на сайте
    @GetMapping("/{id}")
    public String showTheaterInfo(@PathVariable Long id,
                                  Model model) {

        Banner banner = bannerRepo.findById(1L).orElseThrow();
        Movie_Theater theater = repo.findById(id).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());
        model.addAttribute("theater", theater);
        model.addAttribute("hallAmount", theater.getHalls().size());

        return "main_site/theater_page";

    }

    @GetMapping("/{id}/{hallId}")
    public String showTheaterHallInfo(@PathVariable Long id,
                                  @PathVariable Long hallId,
                                  Model model) {

        Banner banner = bannerRepo.findById(1L).orElseThrow();
        Movie_Theater theater = repo.findById(id).orElseThrow();
        Hall hall = hallRepo.findById(new HallId(hallId, id)).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());
        model.addAttribute("theater", theater);
        model.addAttribute("hall", hall);

        return "main_site/hall_page";

    }

    @GetMapping("/{id}/delete")
    public String deleteTheater(@PathVariable Long id, Model model) {
        Movie_Theater theater = repo.findById(id).orElseThrow();
        repo.delete(theater);
        return "redirect:/theaters";
    }
}
