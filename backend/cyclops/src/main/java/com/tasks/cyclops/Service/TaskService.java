package com.tasks.cyclops.Service;

import java.util.List;

import com.tasks.cyclops.dto.TaskDto;
import com.tasks.cyclops.model.TodoEntity;

public interface TaskService {
    
    public String addNewTask(TaskDto taskDto);
    
    List<TodoEntity> findByUserName(String user);
    
    void deleteTask(long id);
    
    void updateTask(long id,String status);
}
