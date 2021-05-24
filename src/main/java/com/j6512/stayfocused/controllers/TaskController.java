package com.j6512.stayfocused.controllers;

import com.j6512.stayfocused.models.Notes;
import com.j6512.stayfocused.models.Task;
import com.j6512.stayfocused.models.TaskList;
import com.j6512.stayfocused.models.TaskStatus;
import com.j6512.stayfocused.models.data.NotesRepository;
import com.j6512.stayfocused.models.data.TaskListRepository;
import com.j6512.stayfocused.models.data.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {

    @Autowired
    TaskListRepository taskListRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    NotesRepository notesRepository;


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
        List<Notes> notes = (List<Notes>) notesRepository.getAllNotesByNotesListId(taskId);
        Iterator<Notes> holder = notes.iterator();
        while (holder.hasNext()) {
            int id = holder.next().getId();
            notesRepository.deleteById(id);
        }

        taskRepository.deleteById(taskId);

        Optional<TaskList> optionalTaskList = taskListRepository.findById(taskListId);
        TaskList taskList = (TaskList) optionalTaskList.get();

        model.addAttribute("taskList", taskList);
        model.addAttribute("tasks", taskList.getTasks());

        return "taskList/view";
    }
}
