package com.project.todo.repository;

import com.project.todo.entity.UserBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBankRepository extends JpaRepository<UserBank, Long> {
}
