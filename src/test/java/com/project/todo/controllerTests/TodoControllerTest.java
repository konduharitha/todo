package com.project.todo.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.todo.controller.TodoController;
import com.project.todo.model.TodoImmutableModel;
import com.project.todo.service.TodoEditService;
import com.project.todo.service.TodoReadService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class) //step:1 add this annotation with controller class name
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc; // step:2 injection mock mvc

    @MockBean
    private TodoEditService todoEditService;

    @MockBean
    private TodoReadService todoReadService; // step : 3 inject beans via annotation @MockBean

    // step : 4 a. create a public void testname(){ }, method should be always public void, cannot be private
    // and doesnot return anything.
    // step : 4 b. annotaion test method with @Test
    @Test
    public void testGetAllTodos() throws Exception {

        TodoImmutableModel mockResponseObject = getTodoImmutableModel();
        List<TodoImmutableModel> mockListResponse = List.of(mockResponseObject);


        Mockito.when(todoReadService.getAllTodos())
                .thenReturn(mockListResponse);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andDo(print());

        Mockito.verify(todoReadService, times(1)).getAllTodos();


    }

    private static TodoImmutableModel getTodoImmutableModel() {
        TodoImmutableModel mockResponseObject = new TodoImmutableModel(1L, "test_title", "testdescription", true, LocalDateTime.now(), null);
        return mockResponseObject;
    }

    @Test
    public void testGetTodoById() throws Exception {

        TodoImmutableModel mockResponseObject = getTodoImmutableModel();

        Mockito.when(todoReadService.getTodoById(anyLong()))
                        .thenReturn(mockResponseObject);

        mockMvc.perform(get("/4"))
                .andExpect(status().isOk())
                .andDo(print());

        Mockito.verify(todoReadService,times(1)).getTodoById(anyLong());

    }

    @Test
    public void testCreateTodo() throws Exception {

        TodoImmutableModel mockResultObject = new TodoImmutableModel(1L, "test_title", "testdescription", true, null, null);

        String mockRequest = new ObjectMapper().writeValueAsString(mockResultObject);

        Mockito.when(todoEditService.saveTodo(any()))
                        .thenReturn(mockResultObject);

        mockMvc.perform(post("/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockRequest)
                )
                .andExpect(status().isOk())
                .andDo(print());

        Mockito.verify(todoEditService,times(1)).saveTodo(any());

    }

//todo : add test case for delete and update api


}
