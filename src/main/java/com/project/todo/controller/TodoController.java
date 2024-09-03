package com.project.todo.controller;

import com.project.todo.model.TodoImmutableModel;
import com.project.todo.service.TodoReadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class TodoController {

    private final TodoReadService todoReadService;

    public TodoController(TodoReadService todoReadService) {
        this.todoReadService = todoReadService;
    }

    @GetMapping()
    public List<TodoImmutableModel> getAllTodos() {
        return this.todoReadService.getAllTodos();
    }

    @GetMapping("/{id}")
    public TodoImmutableModel getTodoById(@PathVariable Long id) {
        return this.todoReadService.getTodoById(id);
    }

}
