package com.tasks.cyclops.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tasks.cyclops.model.TodoEntity;

import jakarta.transaction.Transactional;

public interface TaskRepository extends JpaRepository<TodoEntity, Long> {
    List<TodoEntity> findByUserName(String name);
    
    @Modifying
    @Transactional
    @Query("update TodoEntity t set t.status=:status where t.id=:id")
    void setTaskStatus(@Param(value="id")long id, @Param(value="status")String status);

}
