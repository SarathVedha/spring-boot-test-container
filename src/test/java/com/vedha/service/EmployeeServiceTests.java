package com.vedha.service;

import com.vedha.entity.Employee;
import com.vedha.exception.EmployeeException;
import com.vedha.repository.EmployeeRepository;
import com.vedha.service.impl.EmployeeServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    // Service Unit Testing

    @BeforeEach
    public void setup() {

//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder().id(1L).name("Test").age(21).email("test@gmail.com").build();

    }

    @Test
    @DisplayName("JUnit Test For Save Employee")
    public void givenEmployee_whenSave_thenReturnEmployee() {

        // given - pre-condition or setup data
        // Mocking the employeeService.saveEmployee() method internal employeeRepository methods
        // Behaviour Driven Development
        // Stub Data
        given(employeeRepository.findEmployeeByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        // when - action or the behaviour that we are going to test
        Employee employee1 = employeeService.saveEmployee(employee);
        System.out.println(employee1);

        // then - verify the output
        assertThat(employee1).isNotNull();
    }

    @Test
    @DisplayName("JUnit Test For Save Employee Negative")
    public void givenEmployee_whenSave_thenReturnEmployeeNegative() {

        // given - pre-condition or setup data
        // Mocking the employeeService.saveEmployee() method internal employeeRepository methods
        // Behaviour Driven Development
        //Stub Data
        given(employeeRepository.findEmployeeByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        // when - action or the behaviour that we are going to test
        Assertions.assertThrows(EmployeeException.class, () -> employeeService.saveEmployee(employee));

        // then -verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("JUnit Test For Find All Employee")
    public void givenEmployees_whenFindAll_thenReturnEmployeeList() {

        // given - pre-condition or setup data
        // Stub Data
        Employee employee1 = Employee.builder().id(2L).name("Test2").age(21).email("test2@gmail.com").build();
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        // when - action or the behaviour that we are going to test
        List<Employee> allEmployee = employeeService.getAllEmployee();
        System.out.println(allEmployee);

        // then - verify the output
        assertThat(allEmployee).isNotNull();
        assertThat(allEmployee).hasSize(2);
    }

    @Test
    @DisplayName("JUnit Test For Find All Employee Negative")
    public void givenEmptyEmployees_whenFindAll_thenReturnEmptyEmployeeList() {

        // given - pre-condition or setup data
        // Stub Data
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when - action or the behaviour that we are going to test
        List<Employee> allEmployee = employeeService.getAllEmployee();
        System.out.println(allEmployee);

        // then - verify the output
        assertThat(allEmployee).isNotNull();
        assertThat(allEmployee).hasSize(0);
    }

    @Test
    @DisplayName("JUnit Test For Get Employee By Id")
    public void givenEmployeeId_whenFindById_thenReturnEmployee() {

        // given - pre-condition or setup data
        // Stub Data
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when - action or the behaviour that we are going to test
        Optional<Employee> employeeById = employeeService.getEmployeeById(1L);
        System.out.println(employeeById);

        // then - verify the output
        assertThat(employeeById).isPresent();
    }

    @Test
    @DisplayName("JUnit Test For update Employee")
    public void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        // given - pre-condition or setup data
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setName("Vedha");

        // when - action or the behaviour that we are going to test
        Employee employee1 = employeeService.updateEmployee(employee);

        // then - verify the output
        assertThat(employee1).isNotNull();
        assertThat(employee1.getName()).isEqualTo("Vedha");
    }

    @Test
    @DisplayName("JUnit Test For Delete Employee By Id")
    public void givenEmployeeId_whenDeleteEmployeeById_thenReturnDeleteCount() {

        // given - pre-condition or setup data
        given(employeeRepository.deleteEmployeeById(1L)).willReturn(1L);

        // when - action or the behaviour that we are going to test
        Long l = employeeService.deleteEmployeeById(1L);

        // then - verify the output
        assertThat(l).isEqualTo(1);
    }

    @Test
    @DisplayName("JUnit Test For Delete Employee By Id Not Return")
    public void givenEmployeeId_whenDeleteEmployeeById_thenReturnNothing() {

        // given - pre-condition or setup data
        willDoNothing().given(employeeRepository).deleteById(1L);

        // when - action or the behaviour that we are going to test
        employeeService.deleteEmployeeByIdNoReturn(1L);

        // then - verify the output
        verify(employeeRepository, Mockito.times(1)).deleteById(1L);

    }

}