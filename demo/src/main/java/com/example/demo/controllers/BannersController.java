package com.example.demo.controllers;

import com.example.demo.models.Banner;
import com.example.demo.models.Banner_BG;
import com.example.demo.models.Banner_OFF;
import com.example.demo.repo.BannersBGRepository;
import com.example.demo.repo.BannersOFFRepository;
import com.example.demo.repo.BannersRepository;
import com.example.demo.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/banners")
@SuppressWarnings("unused")
public class BannersController {

    @Autowired
    public BannersRepository repo;

    @Autowired
    public BannersBGRepository bg_repo;

    @Autowired
    public BannersOFFRepository off_repo;

    public static int turn_speed_1 = 5;
    public static int turn_speed_2 = 5;

    private static final String dir_name = "banners";
    public static String image_upload_dir = "demo/" + "src/main/resources/static/images/" + dir_name;

    @GetMapping
    public String banners(Model model) {

        Banner banner = repo.findById(1L).orElseThrow();
        Iterable<Banner_BG> bgBannerList = bg_repo.findAll();
        Iterable<Banner_OFF> offBannerList = off_repo.findAll();
        model.addAttribute("banner", banner);
        model.addAttribute("background_banners", bgBannerList);
        model.addAttribute("offer_banners", offBannerList);
        model.addAttribute("speed1", turn_speed_1);
        model.addAttribute("speed2", turn_speed_2);

        return dir_name + "/banners";
    }

    @PostMapping
    public String bannersSubmit(RedirectAttributes redirAttrs,
                                @RequestParam(required = false) int speed1,
                                @RequestParam(required = false) int speed2,
                                @RequestParam(required = false) MultipartFile[] bg_banners,
                                @RequestParam(required = false) MultipartFile[] off_banners,
                                @RequestParam(required = false) MultipartFile website_background_image)
            throws IOException, URISyntaxException {

        turn_speed_1 = speed1;
        turn_speed_2 = speed2;

        Banner banners = new Banner();
        List<MultipartFile> pictureList = new ArrayList<>(List.of(website_background_image));
        banners.setId(1L);

        if(!website_background_image.isEmpty() && website_background_image.getOriginalFilename() != null) {

            banners.setWebsite_background_image(website_background_image.getOriginalFilename());

            repo.save(banners);

            String uploadDir = image_upload_dir;

            for(MultipartFile picture : pictureList) {
                String f = StringUtils.cleanPath(picture.getOriginalFilename());
                if(picture == null) continue;
                FileUploadUtil.saveFile(uploadDir, f, picture);
            }

        }

        //BG BANNERS

        for(MultipartFile picture : bg_banners) {
            Banner_BG banner = new Banner_BG();
            if(!picture.isEmpty() && picture.getOriginalFilename() != null) banner.setFile_name(picture.getOriginalFilename());
            else continue;
            bg_repo.save(banner);
            String uploadDir = image_upload_dir + "/bg_banners";
            FileUploadUtil.saveFile(uploadDir, StringUtils.cleanPath(picture.getOriginalFilename()), picture);
        }

        //OFF BANNERS

        for(MultipartFile picture : off_banners) {
            Banner_OFF banner = new Banner_OFF();
            if(!picture.isEmpty() && picture.getOriginalFilename() != null) banner.setFile_name(picture.getOriginalFilename());
            else continue;
            off_repo.save(banner);
            String uploadDir = image_upload_dir + "/off_banners";
            FileUploadUtil.saveFile(uploadDir, StringUtils.cleanPath(picture.getOriginalFilename()), picture);
        }

        redirAttrs.addFlashAttribute("message", "Баннера обновлены!");

        return "redirect:/banners";
    }

    @GetMapping("/bg/{id}/delete")
    public String bgBannerRemove(@PathVariable Long id, Model model) {

        Banner_BG banner = bg_repo.findById(id).orElseThrow();

        bg_repo.delete(banner);

        return "redirect:/banners";
    }

    @GetMapping("/off/{id}/delete")
    public String offBannerRemove(@PathVariable Long id, Model model) {

        Banner_OFF banner = off_repo.findById(id).orElseThrow();

        off_repo.delete(banner);

        return "redirect:/banners";
    }

}
