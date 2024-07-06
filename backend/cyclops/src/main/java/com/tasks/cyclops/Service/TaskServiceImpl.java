package com.tasks.cyclops.Service;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tasks.cyclops.Repository.TaskRepository;
import com.tasks.cyclops.dto.TaskDto;
import com.tasks.cyclops.model.TodoEntity;

@Service
public class TaskServiceImpl implements TaskService{
    @Autowired
    private TaskRepository listRepo;

    public TaskServiceImpl(TaskRepository listRepo){
        this.listRepo=listRepo;
    }
    @Override
    public String addNewTask(TaskDto taskDto){
        TodoEntity task=new TodoEntity();
        task.setUserName(taskDto.getName());
        task.setDueDate(taskDto.getDue());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        listRepo.save(task);
        return "Task Created";
    }
    
    @Override
    public List<TodoEntity> findByUserName(String user){
        return listRepo.findByUserName(user);
    }
    @Override
    public void deleteTask(long id){
        listRepo.deleteById(id);
    }
    @Override
    public void updateTask(long id,String status){
        listRepo.setTaskStatus(id,status);
    }
    public List<TodoEntity> findSortedTasks(String name, String sorter){
        return sortedTask(name,sorter);
    }
    private List<TodoEntity> sortedTask(String name,String sorter){
        Predicate<? super TodoEntity> predicate=list->list.getStatus().equalsIgnoreCase(sorter);
        return listRepo.findByUserName(name).stream().filter(predicate).toList();
    }
}
