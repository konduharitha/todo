package com.project.todo.model;

import java.time.LocalDateTime;
import java.util.Objects;

public final class TodoImmutableModel {

    private final Long id;
    private final String title;
    private final String description;

    private final Boolean completed;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public TodoImmutableModel(Long id, String title, String description, Boolean completed, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoImmutableModel todo = (TodoImmutableModel) o;
        return completed == todo.completed &&
                Objects.equals(id, todo.id) &&
                Objects.equals(title, todo.title) &&
                Objects.equals(description, todo.description) &&
                Objects.equals(createdAt, todo.createdAt) &&
                Objects.equals(updatedAt, todo.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, completed, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
