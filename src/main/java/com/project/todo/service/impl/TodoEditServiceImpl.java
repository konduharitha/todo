package com.project.todo.service.impl;

import com.project.todo.entity.TodoEntity;
import com.project.todo.model.TodoImmutableModel;
import com.project.todo.repository.TodoRepository;
import com.project.todo.service.TodoEditService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TodoEditServiceImpl implements TodoEditService {

    private final TodoRepository todoRepository;

    public TodoEditServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoImmutableModel saveTodo(TodoImmutableModel todoImmutableModel) {
        //1.map to todoentity
        //2.save to repository
        //3.again map to immutable model form save method.
        TodoEntity todoEntity=new TodoEntity();
         todoEntity.setId(todoImmutableModel.getId());
         todoEntity.setTitle(todoImmutableModel.getTitle());
         todoEntity.setDescription(todoImmutableModel.getDescription());
         todoEntity.setCompleted(Boolean.FALSE);
         todoEntity.setCreatedAt(LocalDateTime.now());

        TodoEntity savedEntity = todoRepository.save(todoEntity);

        TodoImmutableModel todoImmutableModel1= new TodoImmutableModel(
                savedEntity.getId(),
                savedEntity.getTitle(),
                savedEntity.getDescription(),
                savedEntity.getCompleted(),
                savedEntity.getCreatedAt(),
                savedEntity.getUpdatedAt()
        );

      return todoImmutableModel1;


    }

    @Override
    public String deleteTodo(Long id) {
        if(todoRepository.findById(id).isPresent()) {
            todoRepository.deleteById(id);
            return "Succesfully deleted Todo with Id = " + id;
        }else {
            return "Id " + id + " doesn't exist";
        }

    }

    @Override
    public TodoImmutableModel updateTodo(TodoImmutableModel request) {
        //1.get data from db/repo
        //2.update values except id
        //3.save the updated entity in repo
        //4.saved entity convert to immutable model and return
        Optional<TodoEntity> savedEntity = todoRepository.findById(request.getId());
        if(savedEntity.isPresent()){
            TodoEntity todoEntity = savedEntity.get();
            todoEntity.setTitle(request.getTitle());
            todoEntity.setCompleted(request.isCompleted());
            todoEntity.setDescription(request.getDescription());
            todoEntity.setUpdatedAt(LocalDateTime.now());

            TodoEntity updatedTodo = todoRepository.save(todoEntity);

            TodoImmutableModel todoImmutableModel = new TodoImmutableModel(
                    updatedTodo.getId(),
                    updatedTodo.getTitle(),
                    updatedTodo.getDescription(),
                    updatedTodo.getCompleted(),
                    updatedTodo.getCreatedAt(),
                    updatedTodo.getUpdatedAt()

            );
            return todoImmutableModel;
        }else {
            return new TodoImmutableModel(null,null,null,null,null,null);
        }
    }
}
