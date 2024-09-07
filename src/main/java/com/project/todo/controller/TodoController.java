package com.project.todo.controller;

import com.project.todo.model.TodoImmutableModel;
import com.project.todo.service.TodoEditService;
import com.project.todo.service.TodoReadService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class TodoController {

    private final TodoReadService todoReadService;
    private final TodoEditService todoEditService;


    public TodoController(TodoReadService todoReadService, TodoEditService todoEditService) {
        this.todoReadService = todoReadService;
        this.todoEditService = todoEditService;
    }


    @GetMapping()
    public List<TodoImmutableModel> getAllTodos() {
        return this.todoReadService.getAllTodos();
    }

    @GetMapping("/{id}")
    public TodoImmutableModel getTodoById(@PathVariable Long id) {
        return this.todoReadService.getTodoById(id);
    }

    @PostMapping("/save")
    public TodoImmutableModel createTodo(@RequestBody TodoImmutableModel request){
       return this.todoEditService.saveTodo(request);
    }

    @DeleteMapping("/{id}")
    public String deleteTodo(@PathVariable Long id){
       return todoEditService.deleteTodo(id);
    }

    @PutMapping("/update")
    public TodoImmutableModel updateTodo(@RequestBody TodoImmutableModel request){
        return todoEditService.updateTodo(request);

    }
}
