package com.project.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TodoController.class) //step:1 add this annotation with controller class name
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc; // step:2 injection mock mvc, used for api testing

    @MockBean
    private TodoEditService todoEditService;

    @MockBean
    private TodoReadService todoReadService; // step : 3 inject beans via annotation @MockBean

    // step : 4 a. create a public void testname(){ }, method should be always public void, cannot be private
    // and doesnot return anything.
    // step : 4 b. annotaion test method with @Test
    //we can add life cycle methods @BeforeEach, @BeforeAll, @AfterEach, @AfterAll

    private static TodoImmutableModel getTodoImmutableModel() {
        TodoImmutableModel mockResponseObject = new TodoImmutableModel(1L, "test_title", "testdescription", true, LocalDateTime.now(), null);
        return mockResponseObject;
    }

    @Test
    public void testGetAllTodos() throws Exception {

        TodoImmutableModel mockResponseObject = getTodoImmutableModel();
        List<TodoImmutableModel> mockListResponse = List.of(mockResponseObject);

        //Why Mockito is used ?
        //it is used for mock external service method calls.
        //here todoReadService.getAllTodos() is a external service method call for the controller bean/class, and return mock response.

        Mockito.when(todoReadService.getAllTodos())
                .thenReturn(mockListResponse);

        //this make mock api call to the controller end point

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andDo(print());

        //to verfiy how many external service methods calls were made, when the request has been send to api
        //below todoReadService is a external service and getALlTodos() is a method call and we are verifying single invocation when we hit this get API

        Mockito.verify(todoReadService, times(1)).getAllTodos();


    }

    @Test
    public void testGetTodoById() throws Exception {

        TodoImmutableModel mockResponseObject = getTodoImmutableModel();

        Mockito.when(todoReadService.getTodoById(anyLong()))
                .thenReturn(mockResponseObject);

        mockMvc.perform(get("/4"))
                .andExpect(status().isOk())
                .andDo(print());

        Mockito.verify(todoReadService, times(1)).getTodoById(anyLong());

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

        Mockito.verify(todoEditService, times(1)).saveTodo(any());

    }

//todo : add test case for delete and update api

    @Test
    public void testDeleteTodo() throws Exception {

        String expectedMessage = "Deleted Succesfully";

        Mockito.when(todoEditService.deleteTodo(anyLong()))
                .thenReturn(expectedMessage);


        mockMvc.perform(delete("/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));

        Mockito.verify(todoEditService, times(1)).deleteTodo(anyLong());

    }

    @Test
    public void testUpdateTodo() throws Exception {

        TodoImmutableModel mockResultObject = new TodoImmutableModel(1L, "test_title1", "testdescription1", true, null, null);
        String mockRequest = new ObjectMapper().writeValueAsString(mockResultObject);

        Mockito.when(todoEditService.updateTodo(any()))
                .thenReturn(mockResultObject);


        mockMvc.perform(put("/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockRequest)
                )
                .andExpect(status().isOk())
                .andDo(print());

        Mockito.verify(todoEditService, times(1)).updateTodo(any());

    }


}
