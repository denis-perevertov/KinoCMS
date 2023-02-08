package com.example.demo.controllers;

import com.example.demo.models.Banner;
import com.example.demo.models.Offer;
import com.example.demo.repo.BannersRepository;
import com.example.demo.repo.OffersRepository;
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
import java.util.List;

@Controller
@RequestMapping("/offers")
@SuppressWarnings("unused")
public class OfferController {

    @Autowired
    private OffersRepository repo;

    @Autowired
    public BannersRepository bannerRepo;

    private static String dir_name = "offers";
    public static String image_upload_dir = "demo/" + "src/main/resources/static/images/" + dir_name;

    @GetMapping
    public String offers(Model model) {

        Iterable<Offer> offerList = repo.findAll();
        model.addAttribute("offers", offerList);

        return dir_name + "/offers";
    }

    @GetMapping("/add")
    public String addOffer(Model model) {

        model.addAttribute("offer", new Offer());
        return dir_name + "/offer-add";

    }

    @PostMapping("/add")
    public String offerAddSubmit( @RequestParam String name,
                                 @RequestParam String date,
                                 @RequestParam String description,
                                 @RequestParam(required = false) MultipartFile main_picture,
                                 @RequestParam(required = false) MultipartFile picture1,
                                 @RequestParam(required = false) MultipartFile picture2,
                                 @RequestParam(required = false) MultipartFile picture3,
                                 @RequestParam(required = false) MultipartFile picture4,
                                 @RequestParam(required = false) MultipartFile picture5,
                                 Model model) throws IOException, ParseException, URISyntaxException {

        Offer newOffer = new Offer();
        List<MultipartFile> pictureList = new ArrayList<>(List.of(main_picture, picture1, picture2, picture3, picture4, picture5));

        newOffer.setName(name);
        newOffer.setDate(LocalDate.parse(date));
        newOffer.setDescription(description);

        String fileName = main_picture.getOriginalFilename();

        if(!main_picture.isEmpty()) newOffer.setMain_picture(fileName);
        if(!picture1.isEmpty()) newOffer.setPicture1(picture1.getOriginalFilename());
        if(!picture2.isEmpty()) newOffer.setPicture2(picture2.getOriginalFilename());
        if(!picture3.isEmpty()) newOffer.setPicture3(picture3.getOriginalFilename());
        if(!picture4.isEmpty()) newOffer.setPicture4(picture4.getOriginalFilename());
        if(!picture5.isEmpty()) newOffer.setPicture5(picture5.getOriginalFilename());

        Offer savedOffer = repo.save(newOffer);

        String uploadDir = image_upload_dir + "/" + savedOffer.getId();

        for(MultipartFile picture : pictureList) {
            String f = StringUtils.cleanPath(picture.getOriginalFilename());
            if(picture == null) continue;
            FileUploadUtil.saveFile(uploadDir, f, picture);
        }


        return "redirect:/offers";
    }

    @GetMapping("/{id}")
    public String showOfferPage(@PathVariable Long id, Model model) {

        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());

        Offer offer = repo.findById(id).orElseThrow();
        model.addAttribute("offer", offer);

        return "main_site/offer_page";
    }

    @GetMapping("{id}/edit")
    public String offerEdit(@PathVariable Long id, Model model) {

        Offer offer = repo.findById(id).orElseThrow();
        model.addAttribute("offer", offer);
        return dir_name + "/offer-edit";

    }

    @PostMapping("{id}/edit")
    public String offerEditSubmit(@PathVariable Long id,
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

        Offer newOffer = repo.findById(id).orElseThrow();
        List<MultipartFile> pictureList = new ArrayList<>(List.of(main_picture, picture1, picture2, picture3, picture4, picture5));

        newOffer.setName(name);
        newOffer.setDate(LocalDate.parse(date));
        newOffer.setDescription(description);

        String fileName = main_picture.getOriginalFilename();

        if(!main_picture.isEmpty()) newOffer.setMain_picture(fileName);
        if(!picture1.isEmpty()) newOffer.setPicture1(picture1.getOriginalFilename());
        if(!picture2.isEmpty()) newOffer.setPicture2(picture2.getOriginalFilename());
        if(!picture3.isEmpty()) newOffer.setPicture3(picture3.getOriginalFilename());
        if(!picture4.isEmpty()) newOffer.setPicture4(picture4.getOriginalFilename());
        if(!picture5.isEmpty()) newOffer.setPicture5(picture5.getOriginalFilename());

        Offer savedOffer = repo.save(newOffer);

        String uploadDir = image_upload_dir + "/" + savedOffer.getId();

        for(MultipartFile picture : pictureList) {
            String f = StringUtils.cleanPath(picture.getOriginalFilename());
            if(picture == null) continue;
            FileUploadUtil.saveFile(uploadDir, f, picture);
        }


        return "redirect:/offers";
    }

    @GetMapping("/{id}/delete")
    public String offerRemove(@PathVariable Long id, Model model) {

        Offer offer = repo.findById(id).orElseThrow();

        repo.delete(offer);

        return "redirect:/offers";
    }
}
