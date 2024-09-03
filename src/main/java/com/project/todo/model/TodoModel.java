package com.project.todo.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class TodoModel {

    Long id;
    String title;
    String description;
    boolean completed;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    // You can add custom methods if needed
    public TodoModel markAsCompleted() {
        return TodoModel.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .completed(true)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
