package com.project.todo;

import com.project.todo.entity.TodoEntity;
import com.project.todo.model.TodoImmutableModel;

import java.time.LocalDateTime;
import java.util.List;

public class TodoMockData {

    public static TodoImmutableModel getTodoImmutableModel() {
        return new TodoImmutableModel(1L, "test_title", "testdescription", true, LocalDateTime.now(), null);
    }

    public static List<TodoImmutableModel> getTodoImmutbaleModelList() {
        return List.of(
                getTodoImmutableModel(),
                getTodoImmutableModel(),
                getTodoImmutableModel()
        );
    }

    public static TodoEntity getMockTodoEntity() {
        return new TodoEntity(3L, "todoentity", "return to do Entity object", false, LocalDateTime.now(), LocalDateTime.now());
    }


    public static List<TodoEntity> getMockTodoEntities() {

        return List.of(getMockTodoEntity());

    }


}
