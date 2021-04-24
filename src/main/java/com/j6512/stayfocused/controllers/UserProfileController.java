package com.j6512.stayfocused.controllers;

import com.j6512.stayfocused.models.User;
import com.j6512.stayfocused.models.UserProfile;
import com.j6512.stayfocused.models.data.UserProfileRepository;
import com.j6512.stayfocused.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("profile")
public class UserProfileController {

    @Autowired
    UserProfileRepository userProfileRepository;

    @GetMapping("index")
    public String displayProfile() {

        return "profile/index";
    }

    @GetMapping("create")
    public String displayCreateProfileForm(Model model) {
        model.addAttribute("title", "Create your profile");
        model.addAttribute("userProfile", new UserProfile());
        return "profile/create";
    }

    @PostMapping("create")
    public String processCreateProfileForm(@ModelAttribute @Valid UserProfile userProfile,
                                           Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Creatteee your profile");
            return "profile/create";
        }

        userProfileRepository.save(userProfile);

        return "redirect:";
    }

}
