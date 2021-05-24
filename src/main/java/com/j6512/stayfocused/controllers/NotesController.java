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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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

        Optional<TaskList> optionalTaskList = taskListRepository.findById(taskListId);
        TaskList taskList = (TaskList) optionalTaskList.get();
        model.addAttribute("taskList", taskList);

        Optional<Task> optionalTask = taskRepository.findById(taskId);
        Task task = (Task) optionalTask.get();
        model.addAttribute("task", task);

        model.addAttribute("title", "Notes for Task: " + task.getTitle());
        List<Notes> notesList = task.getNotesList();
        model.addAttribute("notesList", notesList);

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
        List<Notes> notesList = task.getNotesList();
        model.addAttribute("notesList", notesList);

        return "taskList/notes";
    }

    @GetMapping("taskList/edit-notes/{taskListId}/{taskId}/{notesId}")
    public String displayEditNotesForm(@PathVariable int taskListId, @PathVariable int taskId, @PathVariable int notesId,
                                       Model model) {
        Optional<TaskList> optionalTaskList = taskListRepository.findById(taskListId);
        TaskList taskList = (TaskList) optionalTaskList.get();
        model.addAttribute("taskList", taskList);

        Optional<Task> optionalTask = taskRepository.findById(taskId);
        Task task = (Task) optionalTask.get();
        model.addAttribute("task", task);

        Optional<Notes> optionalNotes = notesRepository.findById(notesId);
        Notes notes = (Notes) optionalNotes.get();
        model.addAttribute("notes", notes);

        model.addAttribute("title", "Editing Note: " + notes.getName() + " for Task: " + task.getTitle());

        return "taskList/edit-notes";
    }

    @PostMapping("taskList/edit-notes/{taskListId}/{taskId}/{notesId}")
    public String processEditNotesForm(@ModelAttribute @Valid Notes newNotes, Errors errors, Model model,
                                       @PathVariable int taskListId, @PathVariable int taskId, @PathVariable int notesId,
                                       @RequestParam String name, @RequestParam String description) {
        if (errors.hasErrors()) {
            return "taskList/edit-notes";
        }

        Optional<TaskList> optionalTaskList = taskListRepository.findById(taskListId);
        TaskList taskList = (TaskList) optionalTaskList.get();
        model.addAttribute("taskList", taskList);

        Optional<Task> optionalTask = taskRepository.findById(taskId);
        Task task = (Task) optionalTask.get();
        model.addAttribute("task", task);

        Optional<Notes> optionalNotes = notesRepository.findById(notesId);
        Notes notes = (Notes) optionalNotes.get();
        model.addAttribute("notes", notes);

        newNotes = optionalNotes.get();
        newNotes.setName(name);
        newNotes.setDescription(description);

        notesRepository.save(newNotes);

        model.addAttribute("notesList", task.getNotesList());

        return "taskList/notes";
    }

    @GetMapping("taskList/delete-notes/{taskListId}/{taskId}/{notesId}")
    public String displayDeleteNotesForm(@PathVariable int taskListId, @PathVariable int taskId, @PathVariable int notesId,
                                         Model model) {
        Optional<Notes> optionalNotes = notesRepository.findById(notesId);
        Notes notes = optionalNotes.get();

        model.addAttribute("title", "Delete Note: " + notes.getName());
        model.addAttribute("notes", notes);

        return "taskList/delete-notes";
    }

    @PostMapping("taskList/delete-notes/{taskListId}/{taskId}/{notesId}")
    public String processDeleteNotesForm(@PathVariable int taskListId, @PathVariable int taskId, @PathVariable int notesId,
                                         Model model) {
        notesRepository.deleteById(notesId);

        Optional<Task> optionalTask = taskRepository.findById(taskId);
        Task task = (Task) optionalTask.get();
        model.addAttribute("task", task);

        Optional<TaskList> optionalTaskList = taskListRepository.findById(taskListId);
        TaskList taskList = (TaskList) optionalTaskList.get();
        model.addAttribute("taskList", taskList);

        List<Notes> notesList = task.getNotesList();
        model.addAttribute("notesList", notesList);

        return "taskList/notes";
    }
}