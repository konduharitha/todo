package com.project.todo.service;

import com.project.todo.model.TodoImmutableModel;

public interface TodoEditService {
    TodoImmutableModel saveTodo(TodoImmutableModel todoImmutableModel);

    String deleteTodo(Long id);

    TodoImmutableModel updateTodo(TodoImmutableModel request);
}
