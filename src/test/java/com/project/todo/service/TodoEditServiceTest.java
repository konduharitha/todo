package com.project.todo.service;


import com.project.todo.entity.TodoEntity;
import com.project.todo.model.TodoImmutableModel;
import com.project.todo.repository.TodoRepository;
import com.project.todo.service.impl.TodoEditServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


//@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class TodoEditServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoEditServiceImpl todoEditService;

    @Test
    public void testSaveTodo() {

        TodoImmutableModel todoImmutableModel = new TodoImmutableModel(
                1L, "Test Todo", "This is a test description", false, LocalDateTime.now(), null);

        TodoEntity todoEntity = new TodoEntity(
                todoImmutableModel.getId(),
                todoImmutableModel.getTitle(),
                todoImmutableModel.getDescription(),
                todoImmutableModel.isCompleted(),
                todoImmutableModel.getCreatedAt(),
                todoImmutableModel.getUpdatedAt()
        );

        when(todoRepository.save(any()))
                .thenReturn(todoEntity);

        // 4. Call the method under test
        TodoImmutableModel result = todoEditService.saveTodo(todoImmutableModel);

        // 5. Verify that the returned immutable model has the expected values
        assertEquals(todoEntity.getId(), result.getId());
        assertEquals(todoEntity.getTitle(), result.getTitle());
        assertEquals(todoEntity.getDescription(), result.getDescription());
        assertEquals(todoEntity.getCompleted(), result.isCompleted());
        assertEquals(todoEntity.getCreatedAt(), result.getCreatedAt());
        assertEquals(todoEntity.getUpdatedAt(), result.getUpdatedAt());


    }

    @Test
    public void deleteTodoTest_Success() {

        Long id = 1L;
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setId(id);

        Mockito.when(todoRepository.findById(id))
                .thenReturn(Optional.of(todoEntity));

        doNothing().when(todoRepository).deleteById(id);

        String result = todoEditService.deleteTodo(id);

        verify(todoRepository, times(1)).findById(id);

        assertEquals("Succesfully deleted Todo with Id = " + id, result);


    }

    @Test
    public void deleteTodoTest_IdDoesnotExists() {

        Long id = 2L;

        Mockito.when(todoRepository.findById(id)).thenReturn(Optional.empty());

        String result = todoEditService.deleteTodo(id);

        verify(todoRepository, times(0)).deleteById(id);

        assertEquals("Id " + id + " doesn't exist", result);

    }

    @Test
    public void updateTodoTest_Success() {
        TodoImmutableModel testInput = new TodoImmutableModel(
                1L, "Updated Title", "Updated Description", true, LocalDateTime.now(), null);


        TodoEntity existingEntity = new TodoEntity();
        existingEntity.setId(1L);
        existingEntity.setTitle("Old Title");
        existingEntity.setDescription("Old Description");
        existingEntity.setCompleted(false);
        existingEntity.setCreatedAt(LocalDateTime.now().minusDays(1));
        existingEntity.setUpdatedAt(LocalDateTime.now().minusDays(1));

        TodoEntity mockUpdatedEntityResponse = new TodoEntity();
        mockUpdatedEntityResponse.setId(1L);
        mockUpdatedEntityResponse.setTitle("Updated Title");
        mockUpdatedEntityResponse.setDescription("Updated Description");
        mockUpdatedEntityResponse.setCompleted(true);
        mockUpdatedEntityResponse.setCreatedAt(LocalDateTime.now().minusDays(1));
        mockUpdatedEntityResponse.setUpdatedAt(LocalDateTime.now().minusDays(1));

        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(existingEntity));

        when(todoRepository.save(any(TodoEntity.class))).thenReturn(mockUpdatedEntityResponse);

        TodoImmutableModel response = todoEditService.updateTodo(testInput);

        verify(todoRepository, times(1)).findById(anyLong()); // Ensure findById is called once
        verify(todoRepository, times(1)).save(any(TodoEntity.class)); // Ensure save is called once

        assertEquals(testInput.getId(), response.getId());
        assertEquals(testInput.getTitle(), response.getTitle());
        assertEquals(testInput.getDescription(), response.getDescription());
        assertEquals(testInput.isCompleted(), response.isCompleted());
        assertNotNull(response.getUpdatedAt());


    }
}
