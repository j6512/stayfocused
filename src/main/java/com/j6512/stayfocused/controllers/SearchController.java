package com.j6512.stayfocused.controllers;

import com.j6512.stayfocused.models.Task;
import com.j6512.stayfocused.models.TaskList;
import com.j6512.stayfocused.models.User;
import com.j6512.stayfocused.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class SearchController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private TaskRepository taskRepository;

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

    @GetMapping("search")
    public String displaySearchForm(Model model) {
        model.addAttribute("title", "Search");

        return "search";
    }

   @PostMapping("search")
    public String processSearchForm(Model model, HttpServletRequest request,
                                    @RequestParam String searchType, @RequestParam String searchTerm) {
        HttpSession session = request.getSession(false);
        User user = getUserFromSession(session);
        model.addAttribute("user", user);
        model.addAttribute("title", "searching");

        Iterable<TaskList> taskLists;
        Iterable<Task> tasks;

        if (searchType.toLowerCase().equals("list")) {
            taskLists = TaskListData.findByColumnAndValue(searchType, searchTerm, taskListRepository.findAll());

            model.addAttribute("taskLists", taskLists);
        } else if (searchType.toLowerCase().equals("task")) {
            tasks = TaskData.findByColumnAndValue(searchType, searchTerm, taskRepository.findAll());
            model.addAttribute("tasks", tasks);
        }

        return "search";
   }
}