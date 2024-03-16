package com.vedha.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vedha.entity.Employee;
import com.vedha.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest // Loads Only Web Beans in IOC container
class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    // Controller Unit Testing

    @Test
    @DisplayName("JUnit Test For Create Employee Api")
    public void givenEmployee_whenCreateEmployee_thenReturnEmployee() throws Exception {

        // given - pre-condition or setup data
        Employee vedha = Employee.builder().name("Vedha").age(23).email("vedha@gmail.com").build();
        // Setting The Request as Response
        // BDD Testing
        given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when - action or the behaviour that we are going to test
        ResultActions perform = mockMvc.perform(
                post("/api/employee/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(vedha))
        );

        // then - verify the output
        perform.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Vedha")))
                .andExpect(jsonPath("$.age", is(23)))
                .andExpect(jsonPath("$.email", is("vedha@gmail.com")))
        ;

    }

    @Test
    @DisplayName("JUnit Test For Get All Employees Api")
    public void givenListEmployee_whenGetAllEmployee_thenReturnListEmployee() throws Exception {

        // given - pre-condition or setup data
        List<Employee> build = List.of(
                Employee.builder().id(1L).name("Vedha").age(22).email("vedha@gmail.com").build(),
                Employee.builder().id(2L).name("Vedha2").age(23).email("Vedha2@gmail.com").build()
        );
        given(employeeService.getAllEmployee()).willReturn(build);

        // when - action or the behaviour that we are going to test
        ResultActions perform = mockMvc.perform(
                get("/api/employee/getAll")
        );

        // then - verify the output
        // Json Size, avg, sum are there
        perform.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(build.size())));
    }

    @Test
    @DisplayName("JUnit Test For Find Employee By Id")
    public void givenEmployeeId_whenFindById_thenReturnEmployee() throws Exception {

        // given - pre-condition or setup data
        Employee vedha = Employee.builder().id(1L).name("Vedha").age(23).email("vedha@gmail.com").build();
        given(employeeService.getEmployeeById(vedha.getId())).willReturn(Optional.of(vedha));

        // when - action or the behaviour that we are going to test
        ResultActions perform = mockMvc.perform(get("/api/employee/getById").param("employeeId", String.valueOf(vedha.getId())));

        // then - verify the output
        perform.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(vedha.getName())))
                .andExpect(jsonPath("$.age" , is(vedha.getAge())))
                .andExpect(jsonPath("$.email", is(vedha.getEmail())))
        ;

    }

    @Test
    @DisplayName("JUnit Test For Find By Id Empty Employee Negative")
    public void givenEmployeeId_whenFindById_thenReturnEmptyEmployee() throws Exception {

        // given - pre-condition or setup data
        given(employeeService.getEmployeeById(2L)).willReturn(Optional.empty());

        // when - action or the behaviour that we are going to test
        ResultActions perform = mockMvc.perform(get("/api/employee/getById").param("employeeId", "2"));

        // then - verify the output
        perform.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JUnit Test For update Employee By Id")
    public void givenUpdatedEmployee_whenUpdatedEmployeeById_thenReturnUpdatedEmployee() throws Exception{

        // given - pre-condition or setup data
        Employee vedha = Employee.builder().id(1L).name("Vedha").age(22).email("vedha@gmail.com").build();
        Employee vedha2 = Employee.builder().id(1L).name("Vedha2").age(23).email("Vedha2@gmail.com").build();

        given(employeeService.getEmployeeById(vedha.getId())).willReturn(Optional.of(vedha));
//        given(employeeService.updateEmployee(vedha2)).willReturn(vedha2);
        given(employeeService.updateEmployee(any(Employee.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when - action or the behaviour that we are going to test
        ResultActions perform = mockMvc.perform(
                put("/api/employee/updateById")
                        .param("employeeId", String.valueOf(vedha.getId()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(vedha2))
        );

        // then - verify the output
        perform.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(vedha2.getName())))
                .andExpect(jsonPath("$.age", is(vedha2.getAge())))
                .andExpect(jsonPath("$.email", is(vedha2.getEmail())))
        ;
    }

    @Test
    @DisplayName("JUnit Test For update Employee By Id Negative")
    public void givenUpdatedEmployeeWithWrongId_whenUpdateEmployeeById_thenReturnEmptyEmployee() throws Exception {

        // given - pre-condition or setup data
        Employee vedha2 = Employee.builder().id(1L).name("Vedha2").age(23).email("Vedha2@gmail.com").build();
        given(employeeService.getEmployeeById(2L)).willReturn(Optional.empty());
        given(employeeService.updateEmployee(any(Employee.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when - action or the behaviour that we are going to test
        ResultActions perform = mockMvc.perform(
                put("/api/employee/updateById")
                        .param("employeeId", "2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(vedha2))
        );

        // then - verify the output
        perform.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JUnit Test For Delete By Id")
    public void givenEmployee_whenDeleteById_thenReturnCount() throws Exception {

        // given - pre-condition or setup data
        given(employeeService.deleteEmployeeById(1L)).willReturn(1L);

        // when - action or the behaviour that we are going to test
        ResultActions perform = mockMvc.perform(delete("/api/employee/deleteById").param("employeeId", "1"));

        // then - verify the output
        perform.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.deleteCount", is(1)));
    }

}