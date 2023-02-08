package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequestMapping("/users")
@SuppressWarnings("unused")
public class UserController {

    private static final String dir_name = "users";

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String users(Model model) {

        Iterable<User> userList = userRepository.findAll();
        model.addAttribute("users", userList);


        return dir_name + "/users";
    }

    @GetMapping("/add")
    public String addUserView(Model model) {

        model.addAttribute("user", new User());
        return dir_name + "/add-user";

    }

    @PostMapping("/add")
    public String addUser(RedirectAttributes redirAttrs,
                          @ModelAttribute User user,
                          @RequestParam String name,
                          @RequestParam String email) {


        try {
            user.setRegistration_date(LocalDateTime.now());
            userRepository.save(user);
        } catch(Exception e) {
            redirAttrs.addFlashAttribute("error_message", "Something went wrong. Exception: " + e.getClass());
            return "redirect:/users/add";
        }

        redirAttrs.addFlashAttribute("success_message", "Добавлен новый пользователь! (id=" + user.getUser_id() + ")");
        return "redirect:/users/add";
    }


    @GetMapping("/{id}/edit")
    public String userEdit(@PathVariable long id, Model model) {

        User user = userRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);

        model.addAttribute("standardDate", new Date());
        model.addAttribute("localDateTime", LocalDateTime.now());
        model.addAttribute("localDate", LocalDate.now());
        model.addAttribute("timestamp", Instant.now());


        return dir_name + "/user-edit-page";

    }

    @PostMapping("/{id}/edit")
    public String userUpdate(@PathVariable long id, @ModelAttribute User user) {

        User user_to_edit = userRepository.findById(id).get();

        user_to_edit.setUser_id(id);
        user_to_edit.setName(user.getName());
        user_to_edit.setSurname(user.getSurname());
        user_to_edit.setCity(user.getCity());
        user_to_edit.setAlias(user.getAlias());
        user_to_edit.setBirth_date(user.getBirth_date());
        user_to_edit.setGender(user.getGender());
        user_to_edit.setEmail(user.getEmail());
        user_to_edit.setPhone_number(user.getPhone_number());

        System.out.println(user_to_edit.getUser_id());

        userRepository.save(user_to_edit);


        return "redirect:/users";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable long id) {
        User user_to_delete = userRepository.findById(id).orElseThrow();

        userRepository.delete(user_to_delete);

        return "redirect:/users";
    }






}
