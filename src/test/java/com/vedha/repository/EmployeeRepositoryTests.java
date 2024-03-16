package com.vedha.repository;

import com.vedha.entity.Employee;
//import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat; // imported Class method as static

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // To Use MySql Or Oracle
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Repository Unit Testing

    private Employee employee;

    // JUnit Test BeforeEach executes before and then All Unit Test method will be executed in this class
    @BeforeEach
    public void setup() {

        employee = Employee.builder().name("Test").age(12).email("test@gmail.com").build();
        employeeRepository.deleteAll();
    }

    // JUnit test using assertJ for save employee operation
    @Test
    @DisplayName("JUnit Test For Save Employee")
    public void givenEmployee_whenSave_thenReturnSavedEmployee() {

        // given - pre-condition or setup data
//        Employee test = Employee.builder().name("Test").age(12).email("test@gmail.com").build();

        // when - action or the behaviour that we are going to test
        Employee save = employeeRepository.save(employee);

        // then - verify the output
//        Assertions.assertThat(save).isNotNull();
//        Assertions.assertThat(save.getId()).isGreaterThan(0);

        assertThat(save).isNotNull();
        assertThat(save.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("JUnit Test For FindAll Employee")
    public void givenEmployeeLists_whenFindAll_thenReturnEmployeeLists() {

        // given - pre-condition or setup data
        employeeRepository.save(employee);

        employee = Employee.builder().name("Test2").age(13).email("test2@gmail.com").build();
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        List<Employee> all = employeeRepository.findAll();

        // then - verify the output
        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("JUnit Test For Find Employee ById")
    public void givenEmployee_whenFindById_thenReturnEmployee() {

        // given - pre-condition or setup data
        Employee save = employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        Optional<Employee> byId = employeeRepository.findById(save.getId());

        // then - verify the output
        assertThat(byId).isNotEmpty();
    }

    @Test
    @DisplayName("JUnit Test For Find Employee ByEmail")
    public void givenEmployee_whenFindByEmail_thenReturnEmployee() {

        // given - pre-condition or setup data
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        Optional<Employee> byEmail = employeeRepository.findEmployeeByEmail(employee.getEmail());

        // then - verify the output
        assertThat(byEmail).isNotEmpty();
    }

    @Test
    @DisplayName("JUnit Test For update Employee ById")
    public void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        // given - pre-condition or setup data
        Employee save = employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        Employee employee = employeeRepository.findById(save.getId()).orElse(null);
        assertThat(employee).isNotNull();
        employee.setName("Test6");
        employee.setEmail("test6@gmail.com");
        Employee save1 = employeeRepository.save(employee);

        // then - verify the output
        assertThat(save1.getName()).isEqualTo("Test6");
        assertThat(save1.getEmail()).isEqualTo("test6@gmail.com");
    }

    @Test
    @DisplayName("JUnit Test For Delete Employee")
    public void givenEmployee_whenDelete_thenDeleteEmployee() {

        // given - pre-condition or setup data
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        employeeRepository.deleteById(employee.getId()); // test7 existing entity object id will be updated by save method

        // then - verify the output
        Optional<Employee> byId = employeeRepository.findById(employee.getId());
        assertThat(byId).isEmpty();
    }

    @Test
    @DisplayName("JUnit Test For Find Employee By JPQL Index Param")
    public void givenEmployee_whenFindByJpqlIndex_thenReturnEmployee() {

        // given - pre-condition or setup data
        Employee save = employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        Optional<Employee> byJPQLEmailAndName = employeeRepository.findByJPQLIndexParam(save.getEmail(), save.getName());

        // then - verify the output
        assertThat(byJPQLEmailAndName).isNotEmpty();
    }

    @Test
    @DisplayName("JUnit Test For Find Employee By JPQL Named Param")
    public void givenEmployee_whenFindByJpqlNamedParam_thenReturnEmployee() {

        // given - pre-condition or setup data
        Employee save = employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        Optional<Employee> byJPQLEmailAndName = employeeRepository.findByJPQLNamedParam(save.getEmail(), save.getName());

        // then - verify the output
        assertThat(byJPQLEmailAndName).isNotEmpty();
    }

    @Test
    @DisplayName("JUnit Test For Find Employee By JPQL Native Index Param")
    public void givenEmployee_whenFindByJpqlNativeIndexParam_thenReturnEmployee() {

        // given - pre-condition or setup data
        Employee save = employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        Optional<Employee> byJPQLEmailAndName = employeeRepository.findByJPQLNativeIndexParam(save.getEmail(), save.getName());

        // then - verify the output
        assertThat(byJPQLEmailAndName).isNotEmpty();
    }

    @Test
    @DisplayName("JUnit Test For Find Employee By JPQL Native Named Param")
    public void givenEmployee_whenFindByJpqlNativeNamedParam_thenReturnEmployee() {

        // given - pre-condition or setup data
        Employee save = employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        Optional<Employee> byJPQLEmailAndName = employeeRepository.findByJPQLNativeNamedParam(save.getEmail(), save.getName());

        // then - verify the output
        assertThat(byJPQLEmailAndName).isNotEmpty();
    }

    @Test
    @DisplayName("JUnit Test For Delete Employee By Id")
    public void givenEmployee_whenDeleteEmployeeById_thenReturnCount() {

        // given - pre-condition or setup data
        Employee save = employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        Long l = employeeRepository.deleteEmployeeById(save.getId());
        System.out.println(l);

        // then - verify the output
        assertThat(l).isEqualTo(1);
    }
}
