package com.j6512.stayfocused.controllers;

import com.j6512.stayfocused.models.TaskList;
import com.j6512.stayfocused.models.User;
import com.j6512.stayfocused.models.data.TaskListRepository;
import com.j6512.stayfocused.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskListController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskListRepository taskListRepository;

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

    @GetMapping("taskList/index")
    public String displayAllTaskLists(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = getUserFromSession(session);
        model.addAttribute("user", user);

        if (user.getTaskLists() != null) {
            List<TaskList> taskList = user.getTaskLists();
            model.addAttribute("taskLists", taskList);
            model.addAttribute("title", "All Task Lists");
        }

        return "taskList/index";
    }

    @GetMapping("taskList/create")
    public String displayTaskListCreateForm(Model model, HttpServletRequest request) {
        model.addAttribute(new TaskList());
        HttpSession session = request.getSession(false);

        if (session != null) {
            User user = getUserFromSession(session);
            model.addAttribute("user", user);
            model.addAttribute("title", "Create a List");
        }

        return "taskList/create";
    }

    @PostMapping("taskList/create")
    public String processTaskListCreateForm(@ModelAttribute @Valid TaskList newTaskList,
                                            Errors errors, Model model,
                                            HttpServletRequest request) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Please create a list");

            return "taskList/create";
        }

        HttpSession session = request.getSession(false);
        User user = getUserFromSession(session);
        model.addAttribute("user", user);


        newTaskList.setUser(user);

        taskListRepository.save(newTaskList);

        return "redirect:/taskList/index";
    }
}