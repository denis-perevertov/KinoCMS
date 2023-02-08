package com.example.demo.controllers;

import com.example.demo.models.Banner;
import com.example.demo.models.News;
import com.example.demo.repo.BannersRepository;
import com.example.demo.repo.NewsRepository;
import com.example.demo.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/news")
@SuppressWarnings("unused")
public class NewsController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    public BannersRepository bannerRepo;

    private static final String dir_name = "news";
    public static String image_upload_dir = "demo/" + "src/main/resources/static/images/" + dir_name;

    @GetMapping
    public String news(Model model) {
        Iterable<News> newsList = newsRepository.findAll();
        model.addAttribute("news", newsList);
        return dir_name + "/news";
    }

    @GetMapping("/{id}")
    public String showNewsPage(@PathVariable Long id, Model model) {

        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());

        News article = newsRepository.findById(id).orElseThrow();
        model.addAttribute("article", article);

        return "main_site/news_page";
    }

    /*   ADD    */

    @GetMapping("/add")
    public String newsAdd(Model model) {
        model.addAttribute("article", new News());
        return dir_name + "/news-add";
    }

    @PostMapping("/add")
    public String newsAddSubmit(RedirectAttributes redirAttrs,
                                @RequestParam String name,
                                @RequestParam String date,
                                @RequestParam String description,
                                @RequestParam(required = false) MultipartFile main_picture,
                                @RequestParam(required = false) MultipartFile picture1,
                                @RequestParam(required = false) MultipartFile picture2,
                                @RequestParam(required = false) MultipartFile picture3,
                                @RequestParam(required = false) MultipartFile picture4,
                                @RequestParam(required = false) MultipartFile picture5,
                                Model model) throws IOException, ParseException, URISyntaxException {

        News news = new News();
        List<MultipartFile> pictureList = new ArrayList<>(List.of(main_picture, picture1, picture2, picture3, picture4, picture5));

        news.setName(name);
        news.setDate(LocalDate.parse(date));
        news.setDescription(description);

        String fileName = main_picture.getOriginalFilename();

        if(!main_picture.isEmpty()) {
            news.setMain_picture(fileName);
        }
        if(!picture1.isEmpty()) {
            news.setPicture1(picture1.getOriginalFilename());
        }
        if(!picture2.isEmpty()) {
            news.setPicture2(picture2.getOriginalFilename());
        }
        if(!picture3.isEmpty()) {
            news.setPicture3(picture3.getOriginalFilename());
        }
        if(!picture4.isEmpty()) {
            news.setPicture4(picture4.getOriginalFilename());
        }
        if(!picture5.isEmpty()) {
            news.setPicture5(picture5.getOriginalFilename());
        }

        News savedNews = newsRepository.save(news);

        String uploadDir = uploadPath + "/" + dir_name + "/" + savedNews.getNews_id();

        for(MultipartFile picture : pictureList) {
            String f = StringUtils.cleanPath(picture.getOriginalFilename());
            if(picture == null) continue;
            FileUploadUtil.saveFile(uploadDir, f, picture);
        }

        return "redirect:/news";
    }

    /*   UPDATE    */

    @GetMapping("{id}/edit")
    public String newsEdit(@PathVariable Long id, Model model) {

        News news = newsRepository.findById(id).orElseThrow();
        model.addAttribute("article", news);
        return dir_name + "/news-edit";

    }

    @PostMapping("{id}/edit")
    public String newsEditSubmit(@PathVariable Long id,
                                 @RequestParam String name,
                                 @RequestParam String date,
                                 @RequestParam String description,
                                 @RequestParam(required = false) MultipartFile main_picture,
                                 @RequestParam(required = false) MultipartFile picture1,
                                 @RequestParam(required = false) MultipartFile picture2,
                                 @RequestParam(required = false) MultipartFile picture3,
                                 @RequestParam(required = false) MultipartFile picture4,
                                 @RequestParam(required = false) MultipartFile picture5,
                                 Model model) throws IOException, ParseException, URISyntaxException {

        News news = newsRepository.findById(id).orElseThrow();
        List<MultipartFile> pictureList = new ArrayList<>(List.of(main_picture, picture1, picture2, picture3, picture4, picture5));

        news.setName(name);
        news.setDate(LocalDate.parse(date));
        news.setDescription(description);

        String fileName = main_picture.getOriginalFilename();

        if(!main_picture.isEmpty()) {
            news.setMain_picture(fileName);
        }
        if(!picture1.isEmpty()) {
            news.setPicture1(picture1.getOriginalFilename());
        }
        if(!picture2.isEmpty()) {
            news.setPicture2(picture2.getOriginalFilename());
        }
        if(!picture3.isEmpty()) {
            news.setPicture3(picture3.getOriginalFilename());
        }
        if(!picture4.isEmpty()) {
            news.setPicture4(picture4.getOriginalFilename());
        }
        if(!picture5.isEmpty()) {
            news.setPicture5(picture5.getOriginalFilename());
        }

        News savedNews = newsRepository.save(news);

        String uploadDir = uploadPath + "/" + dir_name + "/" + savedNews.getNews_id();

        for(MultipartFile picture : pictureList) {
            String f = StringUtils.cleanPath(picture.getOriginalFilename());
            if(picture == null) continue;
            FileUploadUtil.saveFile(uploadDir, f, picture);
        }

        return "redirect:/news";
    }

    /*   DELETE       */

    @GetMapping("/{id}/delete")
    public String newsRemove(@PathVariable Long id, Model model) {
        News news = newsRepository.findById(id).orElseThrow();
        newsRepository.delete(news);
        return "redirect:/news";
    }

}
