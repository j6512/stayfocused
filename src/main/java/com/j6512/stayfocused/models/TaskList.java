package com.j6512.stayfocused.models;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TaskList extends AbstractEntity {

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "taskList")
    private final List<Task> tasks = new ArrayList<>();

    @NotBlank
    @Size(min = 1, max = 50, message = "Please enter a name for your list. The list must be between 1 and 50 characters.")
    private String name;

    public TaskList() {
    }

    public TaskList(@NotBlank String name, User user) {
        this.name = name;
        this.user = user;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
