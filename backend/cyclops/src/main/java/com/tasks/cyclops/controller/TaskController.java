package com.tasks.cyclops.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tasks.cyclops.Service.TaskRepositoryService;
import com.tasks.cyclops.dto.TaskDto;
import com.tasks.cyclops.model.TodoEntity;

@RestController
public class TaskController {
    @Autowired
    private TaskRepositoryService taskService;

    @GetMapping(path="/tasks/{name}")
    public List<TodoEntity> getMyTasks(@PathVariable String name){
        return taskService.findByUserName(name);
    }

    @PostMapping(path="/tasks/addTask")
    public String addTask(@RequestBody TaskDto taskDto){
        return taskService.addNewTask(taskDto.getName(), taskDto.getDescription(), taskDto.getDue(), taskDto.getStatus());
    }

    @DeleteMapping(path="tasks/remove/{id}")
    public void deleteTask(@PathVariable long id){
        taskService.deleteTask(id);
    }
    @PutMapping(path="tasks/update/{id}/{status}")
    public void updatestatus(@PathVariable long id,@PathVariable String status){
        taskService.updateTask(id, status);
    }
    
}
