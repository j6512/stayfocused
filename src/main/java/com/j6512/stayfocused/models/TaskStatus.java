package com.j6512.stayfocused.models;

public enum TaskStatus {
    INPROGRESS("In Progress"),
    COMPLETED("Completed"),
    ONHOLD("On Hold");

    private final String name;

    TaskStatus(String status) {
        this.name = status;
    }

    public String getStatus() {
        return name;
    }

}
