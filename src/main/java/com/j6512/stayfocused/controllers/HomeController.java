package com.j6512.stayfocused.controllers;

import com.j6512.stayfocused.models.User;
import com.j6512.stayfocused.models.UserProfile;
import com.j6512.stayfocused.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

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
    @GetMapping("index")
    public String index(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = getUserFromSession(session);

        if (user.getUserProfile() == null) {
            model.addAttribute("title", "create your profile");
            model.addAttribute(new UserProfile());

            return "redirect:/profile/create";
        }

        LocalDateTime dateObj = LocalDateTime.now();
        DateTimeFormatter formatDateObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy hh:mm a");
        String formattedDate = dateObj.format(formatDateObj);

        model.addAttribute("title", "stay focused");
        model.addAttribute("greeting", "Hello, " + user.getUserProfile().getFirstName());
        model.addAttribute("date", formattedDate);
        return "index";
    }

    @GetMapping("timer")
    public String timer(Model model) {
        model.addAttribute("title", "timer");

        return "timer";
    }
}