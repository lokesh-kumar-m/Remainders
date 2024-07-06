package com.tasks.cyclops.dto;

import java.time.LocalDate;

// import com.fasterxml.jackson.annotation.JsonFormat;

public class TaskDto {
    private String name;
    private String description;
    private String status;
    // @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDate due;
    public TaskDto(String name, String description, String status, LocalDate due) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.due = due;
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDate getDue() {
        return due;
    }
    public void setDue(LocalDate due) {
        this.due = due;
    }
    
}
