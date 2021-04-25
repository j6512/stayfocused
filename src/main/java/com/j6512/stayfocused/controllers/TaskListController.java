package com.j6512.stayfocused.controllers;

import com.j6512.stayfocused.models.TaskList;
import com.j6512.stayfocused.models.data.TaskListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class TaskListController {

    @Autowired
    private TaskListRepository taskListRepository;

    @GetMapping("taskList/index")
    public String displayAllTaskLists(Model model) {
        model.addAttribute("title", "All Task Lists");
        model.addAttribute("lists", taskListRepository.findAll());

        return "taskList/index";
    }
    @GetMapping("taskList/create")
    public String displayTaskListCreateForm(Model model) {
        model.addAttribute("title", "Create a List");
        model.addAttribute(new TaskList());

        return "taskList/create";
    }

    @PostMapping("taskList/create")
    public String processTaskListCreateForm(@ModelAttribute @Valid TaskList taskList,
                                            Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Please create a list");

            return "taskList/create";
        }

        taskListRepository.save(taskList);

        return "redirect:/taskList/index";
    }
}
