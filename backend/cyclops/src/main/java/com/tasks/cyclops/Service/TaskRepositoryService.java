package com.tasks.cyclops.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tasks.cyclops.Repository.TaskRepository;
import com.tasks.cyclops.model.TodoEntity;

@Service
public class TaskRepositoryService {
    @Autowired
    private TaskRepository listRepo;

    public TaskRepositoryService(TaskRepository listRepo){
        this.listRepo=listRepo;
    }

    public String addNewTask(String user,String description,LocalDate date, String status){
        TodoEntity task=new TodoEntity();
        task.setUserName(user);
        task.setDueDate(date);
        task.setDescription(description);
        task.setStatus(status);
        listRepo.save(task);
        return "Task Created";
    }
    
    public List<TodoEntity> findByUserName(String user){
        return listRepo.findByUserName(user);
    }
    public void deleteTask(long id){
        listRepo.deleteById(id);
    }
    public void updateTask(long id,String status){
        listRepo.setTaskStatus(id,status);
    }
}
