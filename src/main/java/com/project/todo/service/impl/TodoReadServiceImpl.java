package com.project.todo.service.impl;

import com.project.todo.entity.TodoEntity;
import com.project.todo.model.TodoImmutableModel;
import com.project.todo.repository.TodoRepository;
import com.project.todo.service.TodoReadService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoReadServiceImpl implements TodoReadService {

    private final TodoRepository todoRepository;

    public TodoReadServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<TodoImmutableModel> getAllTodos() {

        List<TodoEntity> todoEntities = this.todoRepository.findAll();

        return todoEntities.stream()
                .map(TodoReadServiceImpl::mapTodoEntityToModel)
                .toList();
    }


    private static TodoImmutableModel mapTodoEntityToModel(TodoEntity entity) {
        return new TodoImmutableModel(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getCompleted(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }


    @Override
    public TodoImmutableModel getTodoById(Long id) {

        Optional<TodoEntity> todoEntity = this.todoRepository.findById(id);

        if (todoEntity.isPresent()) {

            TodoEntity entity = todoEntity.get();

            return mapTodoEntityToModel(entity);
        }

        return new TodoImmutableModel(null, null, null, null, null, null);
    }
}


