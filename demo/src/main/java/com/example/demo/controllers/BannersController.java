package com.example.demo.controllers;

import com.example.demo.models.Banner;
import com.example.demo.models.Movie_Theater;
import com.example.demo.repo.BannersRepository;
import com.example.demo.repo.TheaterRepository;
import com.example.demo.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/banners")
public class BannersController {

    @Autowired
    public BannersRepository repo;

    private static final String dir_name = "banners";
    public static String image_upload_dir = "demo/" + "src/main/resources/static/images/" + dir_name;

    @GetMapping
    public String banners(Model model) {

        Banner banner = repo.findById(1L).orElseThrow();
        model.addAttribute("banner", banner);

        return dir_name + "/banners";
    }

    @PostMapping
    public String bannersSubmit(RedirectAttributes redirAttrs,
                                @RequestParam(required = false) MultipartFile website_background_image)
            throws IOException, URISyntaxException {

        Banner banners = new Banner();
        List<MultipartFile> pictureList = new ArrayList<>(List.of(website_background_image));

        banners.setId(1L);

        if(!website_background_image.isEmpty()) banners.setWebsite_background_image(website_background_image.getOriginalFilename());

        Banner saved_banners = repo.save(banners);

        String uploadDir = image_upload_dir;

        for(MultipartFile picture : pictureList) {
            String f = StringUtils.cleanPath(picture.getOriginalFilename());
            if(picture == null) continue;
            FileUploadUtil.saveFile(uploadDir, f, picture);
        }

        redirAttrs.addFlashAttribute("message", "Баннера обновлены!");

        return "redirect:/banners";
    }

}
