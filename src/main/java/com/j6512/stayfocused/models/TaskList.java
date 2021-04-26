package com.j6512.stayfocused.models;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TaskList extends AbstractEntity {

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "taskList")
    private final List<Task> tasks = new ArrayList<>();

    @NotBlank
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
