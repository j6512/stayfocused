package com.j6512.stayfocused.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Notes extends AbstractEntity {

    @ManyToOne
    private Task notesList;
    
    @Size(min = 1, max = 25, message = "must be between 1 and 25 characters")
    private String name;

    @Size(max = 500, message = "cannot be larger than 500 characters")
    private String description;

    public Notes() {

    }

    public Notes(String name, String description, Task notesList) {
        this.name = name;
        this.description = description;
        this.notesList = notesList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task getNotesList() {
        return notesList;
    }

    public void setNotesList(Task notesList) {
        this.notesList = notesList;
    }
}
