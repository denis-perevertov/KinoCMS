package com.example.demo.controllers;


import com.example.demo.models.Page;
import com.example.demo.repo.PageRepository;
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

@Controller
@RequestMapping("/pages")
@SuppressWarnings("unused")
public class PagesController {

    @Autowired
    private PageRepository repo;

    private static final String dir_name = "pages";
    private static final String image_upload_dir = "demo/" + "src/main/resources/static/images/" + dir_name;

    @GetMapping
    public String pages(Model model) {

        Iterable<Page> pageList = repo.findAll();
        model.addAttribute("pages", pageList);

        return dir_name + "/pages";
    }

    @GetMapping("/add")
    public String addPage(Model model) {

        model.addAttribute("page", new Page());

        return dir_name + "/page-add";
    }

    @PostMapping("/add")
    public String addPageSubmit(@RequestParam String name,
                                @RequestParam String description,
                                @RequestParam(required = false) MultipartFile main_picture,
                                @RequestParam(required = false) MultipartFile picture1,
                                @RequestParam(required = false) MultipartFile picture2,
                                @RequestParam(required = false) MultipartFile picture3,
                                @RequestParam(required = false) MultipartFile picture4,
                                @RequestParam(required = false) MultipartFile picture5) throws IOException, URISyntaxException {

        Page page = new Page();
        List<MultipartFile> pictureList = new ArrayList<>(List.of(main_picture, picture1, picture2, picture3, picture4, picture5));

        page.setName(name);
        page.setDate(LocalDate.now());
        page.setDescription(description);

        if(!main_picture.isEmpty()) page.setMain_picture(main_picture.getOriginalFilename());
        if(!picture1.isEmpty()) page.setPicture1(picture1.getOriginalFilename());
        if(!picture2.isEmpty()) page.setPicture2(picture2.getOriginalFilename());
        if(!picture3.isEmpty()) page.setPicture3(picture3.getOriginalFilename());
        if(!picture4.isEmpty()) page.setPicture4(picture4.getOriginalFilename());
        if(!picture5.isEmpty()) page.setPicture5(picture5.getOriginalFilename());

        Page savedPage = repo.save(page);

        String uploadDir = image_upload_dir + "/" + savedPage.getId();

        for(MultipartFile picture : pictureList) {
            String f = StringUtils.cleanPath(picture.getOriginalFilename());
            if(picture == null) continue;
            FileUploadUtil.saveFile(uploadDir, f, picture);
        }

        return "redirect:/pages";
    }



    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable Long id, Model model) {

        Page page = repo.findById(id).orElseThrow();

        model.addAttribute("page", page);

        return dir_name + "/page-info";
    }

    @PostMapping("/{id}/edit")
    public String editPageSubmit(@PathVariable Long id,
                                 @RequestParam String name,
                                 @RequestParam String description,
                                 @RequestParam(required = false) MultipartFile main_picture,
                                 @RequestParam(required = false) MultipartFile picture1,
                                 @RequestParam(required = false) MultipartFile picture2,
                                 @RequestParam(required = false) MultipartFile picture3,
                                 @RequestParam(required = false) MultipartFile picture4,
                                 @RequestParam(required = false) MultipartFile picture5) throws IOException, URISyntaxException {

        Page pageToEdit = repo.findById(id).orElseThrow();
        List<MultipartFile> pictureList = new ArrayList<>(List.of(main_picture, picture1, picture2, picture3, picture4, picture5));

        pageToEdit.setName(name);
        pageToEdit.setDescription(description);
        pageToEdit.setDate(LocalDate.now());

        String fileName = main_picture.getOriginalFilename();

        if(!main_picture.isEmpty() && main_picture.getOriginalFilename() != null) pageToEdit.setMain_picture(fileName);
        if(!picture1.isEmpty() && picture1.getOriginalFilename() != null) pageToEdit.setPicture1(picture1.getOriginalFilename());
        if(!picture2.isEmpty() && picture2.getOriginalFilename() != null) pageToEdit.setPicture2(picture2.getOriginalFilename());
        if(!picture3.isEmpty() && picture3.getOriginalFilename() != null) pageToEdit.setPicture3(picture3.getOriginalFilename());
        if(!picture4.isEmpty() && picture4.getOriginalFilename() != null) pageToEdit.setPicture4(picture4.getOriginalFilename());
        if(!picture5.isEmpty() && picture5.getOriginalFilename() != null) pageToEdit.setPicture5(picture5.getOriginalFilename());

        Page savedPage = repo.save(pageToEdit);

        String uploadDir = image_upload_dir + "/" + savedPage.getId();

        for(MultipartFile picture : pictureList) {
            String f = StringUtils.cleanPath(picture.getOriginalFilename());
            FileUploadUtil.saveFile(uploadDir, f, picture);
        }

        return "redirect:/pages";
    }

    @GetMapping("/{id}/delete")
    public String pageRemove(@PathVariable Long id, Model model) {

        repo.delete(repo.findById(id).orElseThrow());
        return "redirect:/pages";
    }
}
