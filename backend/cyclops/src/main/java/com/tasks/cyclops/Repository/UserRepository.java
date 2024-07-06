package com.tasks.cyclops.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tasks.cyclops.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String name);
    boolean existsByUserName(String name);
}
