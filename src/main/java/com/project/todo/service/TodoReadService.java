package com.project.todo.service;

import com.project.todo.model.TodoImmutableModel;

import java.util.List;

public interface TodoReadService {

    List<TodoImmutableModel> getAllTodos();

    TodoImmutableModel getTodoById(Long id);
}
