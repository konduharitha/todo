package com.project.todo.service;

import com.project.todo.TodoMockData;
import com.project.todo.entity.TodoEntity;
import com.project.todo.model.TodoImmutableModel;
import com.project.todo.repository.TodoRepository;
import com.project.todo.service.impl.TodoReadServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)// step 1 : add this annotation for junit 5 testing.
public class TodoReadServiceTest {

    @Mock // Step 2 : using this annotation inject the dependency beans.
    private TodoRepository todoRepository;

    //in junit we cannot test on interface methods. We can only impl classes bcz it has concrete implementation
    //step 3 : to inject which class or bean going to test
    @InjectMocks
    private TodoReadServiceImpl todoReadService;

    @BeforeAll
    public static void beforeAllTest() {
        System.out.println("this will get executed before all test methods executed ");
    }

    @AfterAll
    public static void afterALlTest() {
        System.out.println("This method will execute after all test method execution");
    }

    @BeforeEach
    public void beforeEachTest() {
        System.out.println("This method will execute before each test method executes");
    }

    @AfterEach
    public void afterEachTest() {
        System.out.println("This method will exeute after each test method executes");
    }

    @Test
    void testGetAllTodos() {
        //1. mock the external service class method calls
        //2. call the service method
        //3. verify the response mockito


        Mockito.when(this.todoRepository.findAll())
                .thenReturn(TodoMockData.getMockTodoEntities());

        List<TodoImmutableModel> response = todoReadService.getAllTodos();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());

        Mockito.verify(todoRepository, Mockito.times(1)).findAll();
    }


    @Test
    void testGetTodoById() {

        TodoEntity mockTodoEntity = TodoMockData.getMockTodoEntity();

        Mockito.when(this.todoRepository.findById(anyLong()))
                .thenReturn(Optional.of(mockTodoEntity));

        TodoImmutableModel response = todoReadService.getTodoById(anyLong());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(mockTodoEntity.getId(), response.getId());
        Assertions.assertEquals(mockTodoEntity.getTitle(), response.getTitle());

        Mockito.verify(todoRepository, Mockito.times(1)).findById(anyLong());


    }

}
