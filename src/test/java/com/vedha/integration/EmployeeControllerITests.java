package com.vedha.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vedha.entity.Employee;
import com.vedha.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // its load full application context
@AutoConfigureMockMvc
public class EmployeeControllerITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Controller Integration Testing

    @BeforeEach
    public void setup() {

        employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("JUnit Test For Create Employee API")
    public void givenEmployee_whenCreateAPI_thenReturnCreatedEmployee() throws Exception {

        // given - pre-condition or setup data
        Employee vedha = Employee.builder().name("Vedha").age(21).email("vedha@gmail.com").build();

        // when - action or the behaviour that we are going to test
        ResultActions perform = mockMvc.perform(post("/api/employee/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(vedha))
        );

        // then - verify the output
        perform.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Vedha")))
                .andExpect(jsonPath("$.age", is(21)))
                .andExpect(jsonPath("$.email", is("vedha@gmail.com")))
        ;
    }

    @Test
    @DisplayName("JUnit Test For Get All Employee API")
    public void givenListEmployee_whenGetAllEmployeeAPI_thenReturnAllEmployee() throws Exception {

        // given - pre-condition or setup data
        List<Employee> build = List.of(
                Employee.builder().name("Vedha").age(21).email("vedha@gmail.com").build(),
                Employee.builder().name("Vedha2").age(22).email("vedha2@gmail.com").build()
        );
        employeeRepository.saveAll(build);

        // when - action or the behaviour that we are going to test
        ResultActions perform = mockMvc.perform(get("/api/employee/getAll"));

        // then - verify the output
        perform.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(2)));

    }

    @Test
    @DisplayName("JUnit Test For Find By Id API")
    public void givenEmployeeId_whenFindById_thenReturnEmployee() throws Exception{

        // given - pre-condition or setup data
        Employee vedha = Employee.builder().name("Vedha").age(21).email("vedha@gmail.com").build();
        employeeRepository.save(vedha);

        // when - action or the behaviour that we are going to test
        ResultActions perform = mockMvc.perform(get("/api/employee/getById").param("employeeId", String.valueOf(vedha.getId())));

        // then - verify the output
        perform.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(Integer.parseInt(String.valueOf(vedha.getId())))));
    }


    @Test
    @DisplayName("JUnit Test For Find By Id API Negative")
    public void givenEmployeeId_whenFindById_thenNotFound() throws Exception {

        // given - pre-condition or setup data
        Employee vedha = Employee.builder().name("Vedha").age(21).email("vedha@gmail.com").build();
        employeeRepository.save(vedha);

        // when - action or the behaviour that we are going to test
        ResultActions perform = mockMvc.perform(get("/api/employee/getById").param("employeeId", "3"));

        // then - verify the output
        perform.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("JUnit Test For Update Employee API")
    public void givenEmployee_whenUpdateEmployee_thenReturnEmployee() throws Exception {

        // given - pre-condition or setup data
        Employee vedha = Employee.builder().name("Vedha").age(21).email("vedha@gmail.com").build();
        employeeRepository.save(vedha);
        Employee vedha2 = Employee.builder().name("Vedha2").age(22).email("vedha2@gmail.com").build();

        // when - action or the behaviour that we are going to test
        ResultActions perform = mockMvc.perform(put("/api/employee/updateById")
                .param("employeeId", String.valueOf(vedha.getId()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(vedha2))
        );

        // then - verify the output
        perform.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Integer.parseInt(String.valueOf(vedha.getId())))))
                .andExpect(jsonPath("$.name", is(vedha2.getName())))
                .andExpect(jsonPath("$.age", is(vedha2.getAge())))
                .andExpect(jsonPath("$.email", is(vedha2.getEmail())));

    }

    @Test
    @DisplayName("JUnit Test For Update Employee API Negative")
    public void givenEmployee_whenUpdateEmployee_thenReturnEmployeeNegative() throws Exception {

        // given - pre-condition or setup data
        Employee vedha = Employee.builder().name("Vedha").age(21).email("vedha@gmail.com").build();
        employeeRepository.save(vedha);
        Employee vedha2 = Employee.builder().name("Vedha2").age(22).email("vedha2@gmail.com").build();

        // when - action or the behaviour that we are going to test
        ResultActions perform = mockMvc.perform(put("/api/employee/updateById")
                .param("employeeId", "3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(vedha2))
        );

        // then - verify the output
        perform.andDo(print()).andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("given_when_then")
    public void givenEmployeeId_whenDeleteById_thenReturnCount() throws Exception {

        // given - pre-condition or setup data
        Employee vedha = Employee.builder().name("Vedha").age(21).email("vedha@gmail.com").build();
        employeeRepository.save(vedha);

        // when - action or the behaviour that we are going to test
        ResultActions perform = mockMvc.perform(delete("/api/employee/deleteById").param("employeeId", String.valueOf(vedha.getId())));

        // then - verify the output
        perform.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.deleteCount", is(1)));

    }

}
