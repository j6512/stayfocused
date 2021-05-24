package com.j6512.stayfocused.controllers;

import com.j6512.stayfocused.models.Notes;
import com.j6512.stayfocused.models.Task;
import com.j6512.stayfocused.models.TaskList;
import com.j6512.stayfocused.models.data.NotesRepository;
import com.j6512.stayfocused.models.data.TaskListRepository;
import com.j6512.stayfocused.models.data.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class NotesController {

    @Autowired
    TaskListRepository taskListRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    NotesRepository notesRepository;

    @GetMapping("taskList/notes/{taskListId}/{taskId}")
    public String displayNotes(@PathVariable int taskListId, @PathVariable int taskId, Model model) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        Task task = (Task) optionalTask.get();
        Optional<TaskList> optionalTaskList = taskListRepository.findById(taskListId);
        TaskList taskList = (TaskList) optionalTaskList.get();

        model.addAttribute("task", task);
        model.addAttribute("taskList", taskList);

        model.addAttribute("title", "Notes for Task: " + task.getTitle());
        model.addAttribute("notes", task.getNotesList());

        return "taskList/notes";
    }

    @GetMapping("taskList/add-notes/{taskListId}/{taskId}")
    public String displayAddNotesForm(@PathVariable int taskListId, @PathVariable int taskId, Model model) {

        model.addAttribute("title", "Add Notes");
        model.addAttribute( new Notes());

        return "taskList/add-notes";
    }

    @PostMapping("taskList/add-notes/{taskListId}/{taskId}")
    public String processAddNotesForm(@ModelAttribute @Valid Notes newNotes, Errors errors, @PathVariable int taskListId, @PathVariable int taskId, Model model) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        Task task = (Task) optionalTask.get();
        model.addAttribute("task", task);

        Optional<TaskList> optionalTaskList = taskListRepository.findById(taskListId);
        TaskList taskList = (TaskList) optionalTaskList.get();
        model.addAttribute("taskList", taskList);

        if (errors.hasErrors()) {
            return "taskList/add-notes";
        }

        newNotes.setNotesList(task);
        notesRepository.save(newNotes);

        model.addAttribute("title", "Add Notes: " + task.getTitle());
        model.addAttribute("note", task.getNotesList());

        return "taskList/notes";
    }
}
