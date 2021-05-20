package com.j6512.stayfocused.controllers;

import com.j6512.stayfocused.models.Task;
import com.j6512.stayfocused.models.TaskList;
import com.j6512.stayfocused.models.TaskStatus;
import com.j6512.stayfocused.models.User;
import com.j6512.stayfocused.models.data.TaskListRepository;
import com.j6512.stayfocused.models.data.TaskRepository;
import com.j6512.stayfocused.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskListController {

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

    @GetMapping("taskList/view/{taskListId}")
    public String displaySelectedTaskList(@PathVariable int taskListId, Model model) {
        Optional<TaskList> optionalTaskList = taskListRepository.findById(taskListId);
        TaskList taskList = (TaskList) optionalTaskList.get();

        model.addAttribute("title", "Viewing Task List: " + taskList.getName());
        model.addAttribute("taskList", taskList);
        model.addAttribute("tasks", taskList.getTasks());

        return "taskList/view";
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

    @GetMapping("taskList/edit/{taskListId}")
    public String displayTaskListEditForm(@PathVariable int taskListId, Model model) {
        Optional<TaskList> optionalTaskList = taskListRepository.findById(taskListId);
        TaskList taskList = (TaskList) optionalTaskList.get();

        model.addAttribute("title", "Edit TaskList: " + taskList.getName());
        model.addAttribute("taskList", taskList);
        return "taskList/edit";
    }

    @PostMapping("taskList/edit/{taskListId}")
    public String processTaskListEditForm(@PathVariable int taskListId, @RequestParam String name, @ModelAttribute @Valid TaskList taskList, Errors errors,
                                          Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit TaskList");

            return "taskList/edit";
        }

        Optional<TaskList> optionalTaskList = taskListRepository.findById(taskListId);
        taskList = (TaskList) optionalTaskList.get();
        taskList.setName(name);
        taskListRepository.save(taskList);

        return "redirect:/taskList/index";
    }

    @GetMapping("taskList/delete/{taskListId}")
    public String displayTaskListDeleteForm(@PathVariable int taskListId, Model model) {
        Optional<TaskList> optionalTaskList = taskListRepository.findById(taskListId);
        TaskList taskList = (TaskList) optionalTaskList.get();
        model.addAttribute("taskList", taskList);
        model.addAttribute("title", "Delete List");

        return "taskList/delete";
    }

    @PostMapping("taskList/delete/{taskListId}")
    public String processTaskListDeleteForm(@PathVariable int taskListId) {

        List<Task> tasks = (List<Task>) taskRepository.getAllTasksByTaskListId(taskListId);
        Iterator<Task> holder = tasks.iterator();

        while(holder.hasNext()) {
            int id = holder.next().getId();
            taskRepository.deleteById(id);
        }

        taskListRepository.deleteById(taskListId);
        return "redirect:/taskList/index";
    }

    @GetMapping("taskList/add-task/{taskListId}")
    public String displayTaskListAddTaskForm(@PathVariable int taskListId, Model model) {
        model.addAttribute("title", "Add Tasks");
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute(new Task());

        return "taskList/add-task";
    }

    @PostMapping("taskList/add-task/{taskListId}")
    public String processTaskListAddTaskForm(@ModelAttribute @Valid Task newTask, Errors errors, @PathVariable int taskListId, Model model,
                                             @RequestParam TaskStatus status) {

        Optional<TaskList> optionalTaskList = taskListRepository.findById(taskListId);
        TaskList taskList = (TaskList) optionalTaskList.get();


        if (errors.hasErrors()) {
            return "taskList/add-task";
        }

        model.addAttribute("taskList", taskList);
        newTask.setStatus(status);
        newTask.setTaskList(taskList);
//        newTask.setTaskListId(taskListId);
        taskRepository.save(newTask);
        model.addAttribute("tasks", taskList.getTasks());
        model.addAttribute("title", "Add Tasks");

        return "taskList/view";
    }

    @GetMapping("taskList/edit-task/{taskListId}/{taskId}")
    public String displayTaskListEditTaskForm(@PathVariable int taskListId,
                                              @PathVariable int taskId, Model model) {
        Optional<TaskList> optionalTaskList = taskListRepository.findById(taskListId);
        TaskList taskList = (TaskList) optionalTaskList.get();
        model.addAttribute("taskList", taskList);

        Optional<Task> optionalTask = taskRepository.findById(taskId);
        Task task = optionalTask.get();

        model.addAttribute("statuses", TaskStatus.values());

        model.addAttribute("title", "Edit Task: " + task.getTitle());
        model.addAttribute("task", task);
        return "taskList/edit-task";
    }

    @PostMapping("taskList/edit-task/{taskListId}/{taskId}")
    public String processTaskListEditTaskForm(@ModelAttribute @Valid Task newTask, Errors errors, Model model,
                                              @PathVariable int taskListId, @PathVariable int taskId,
                                              @RequestParam String title, @RequestParam String description, @RequestParam TaskStatus status) {


        Optional<TaskList> optionalTaskList = taskListRepository.findById(taskListId);
        TaskList taskList = (TaskList) optionalTaskList.get();
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if (errors.hasErrors()) {
            model.addAttribute("taskList", taskList);
            return "taskList/edit-task";
        }

        newTask = optionalTask.get();
        newTask.setTitle(title);
        newTask.setDescription(description);
        newTask.setStatus(status);
        taskRepository.save(newTask);

        model.addAttribute("taskList", taskList);
        model.addAttribute("tasks", taskList.getTasks());

        return "taskList/view";
    }

    @GetMapping("taskList/delete-task/{taskListId}/{taskId}")
    public String displayTaskListDeleteTaskForm(@PathVariable int taskListId, @PathVariable int taskId,
                                                Model model) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        Task task = optionalTask.get();

        model.addAttribute("title", "Edit Task: " + task.getTitle());
        model.addAttribute("task", task);
        return "taskList/delete-task";
    }

    @PostMapping("taskList/delete-task/{taskListId}/{taskId}")
    public String processTaskListDeleteTaskForm(@PathVariable int taskListId, @PathVariable int taskId, Model model) {
        taskRepository.deleteById(taskId);

        Optional<TaskList> optionalTaskList = taskListRepository.findById(taskListId);
        TaskList taskList = (TaskList) optionalTaskList.get();

        model.addAttribute("taskList", taskList);
        model.addAttribute("tasks", taskList.getTasks());

        return "taskList/view";
    }
}