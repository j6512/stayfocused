package com.j6512.stayfocused.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Task extends AbstractEntity {

    private String title;

    private String description;

    private boolean isCompleted;

    @ManyToOne
    @NotNull(message = "which list would you like to add it to?")
    private TaskList taskList;

    public Task() {
    }

    public Task(String title, String description, boolean isCompleted, TaskList taskList) {
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.taskList = taskList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }
}
