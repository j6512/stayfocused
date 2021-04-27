package com.j6512.stayfocused.controllers;

import com.j6512.stayfocused.models.User;
import com.j6512.stayfocused.models.UserProfile;
import com.j6512.stayfocused.models.data.TaskListRepository;
import com.j6512.stayfocused.models.data.UserProfileRepository;
import com.j6512.stayfocused.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;
    
    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("user");

        if (userId == null) {
            return null;
        }

        Optional<User> result = userRepository.findById(userId);

        if (result.isEmpty()) {
            return null;
        }

        return result.get();
    }

    @GetMapping("profile/index")
    public String displayProfile(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = getUserFromSession(session);
        model.addAttribute("user", user);

        if (user.getUserProfile() != null) {
            UserProfile userProfile = user.getUserProfile();

            model.addAttribute("userProfile", userProfile);

            return "profile/index";
        } else {
            model.addAttribute("title", "create your profile");
            model.addAttribute(new UserProfile());

            return "redirect:/profile/create";
        }
    }

    @GetMapping("profile/create")
    public String displayCreateProfileForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            User user = getUserFromSession(session);
            model.addAttribute("user", user);
        }

        model.addAttribute("title", "create your profile");
        model.addAttribute(new UserProfile());

        return "profile/create";
    }

    @PostMapping("profile/create")
    public String processCreateProfileForm(@ModelAttribute @Valid UserProfile userProfile,
                                           Errors errors, Model model,
                                           HttpServletRequest request) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "create your profilerror");

            return "profile/create";
        }

        HttpSession session = request.getSession(false);
        User user = getUserFromSession(session);
        model.addAttribute("user", user);

        user.setUserProfile(userProfile);

        userProfileRepository.save(userProfile);

        return "profile/index";
    }

    @GetMapping("profile/edit")
    public String displayEditProfileForm(HttpServletRequest request, UserProfile userProfile,
                                         Model model) {
        model.addAttribute("title", "edit your profile");

        HttpSession session = request.getSession(false);
        User user = getUserFromSession(session);
        userProfile = user.getUserProfile();

        model.addAttribute("userProfile", userProfile);
        model.addAttribute("user", user);

        return "profile/edit";
    }

    @PostMapping("profile/edit")
    public String processEditProfileForm(@ModelAttribute @Valid UserProfile userProfile,
                                         Errors errors, Model model,
                                         @RequestParam String firstName, @RequestParam String lastName,
                                         @RequestParam String location, @RequestParam String bio,
                                         HttpServletRequest request) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "edit your profile");

            return "profile/edit";
        }

        HttpSession session = request.getSession(false);
        User user = getUserFromSession(session);
        model.addAttribute("user", user);

        userProfile = user.getUserProfile();
        userProfileRepository.findById(userProfile.getId());

        userProfile.setFirstName(firstName);
        userProfile.setLastName(lastName);
        userProfile.setLocation(location);
        userProfile.setBio(bio);

        userProfileRepository.save(userProfile);
        user.setUserProfile(userProfile);
        userRepository.save(user);

        return "redirect:/profile/index";
    }
}
