package com.example.demo.services;

import com.example.demo.models.Banner;
import com.example.demo.repo.BannersBGRepository;
import com.example.demo.repo.BannersOFFRepository;
import com.example.demo.repo.BannersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;

@Service
public class BannerService {

    @Autowired
    public BannersRepository bannerRepo;

    @Autowired
    public BannersBGRepository bgBannerRepo;

    @Autowired
    public BannersOFFRepository offBannerRepo;

    public void setBackground(Model model) {
        Banner banner = bannerRepo.findById(1L).orElseThrow();
        model.addAttribute("website_background_image",banner.getWebsite_background_image());
        model.addAttribute("today", LocalDate.now());
    }

    public void insertBannerLists(Model model) {
        model.addAttribute("bg_banners", bgBannerRepo.findAll());
        model.addAttribute("off_banners", offBannerRepo.findAll());
    }
}
