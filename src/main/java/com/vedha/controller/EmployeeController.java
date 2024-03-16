package com.vedha.controller;

import com.vedha.entity.Employee;
import com.vedha.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@Tag(name = "Employee", description = "Employee Apis")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Create Employee", description = "Creates New Employees")
    @ApiResponse(responseCode = "201", description = "HTTP Status 201 Created")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {

        return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
    }

    @Operation(summary = "Get All Employees", description = "Get All Employees")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 Ok")
    @GetMapping(value = "/getAll", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> getAllEmployees() {

        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @Operation(summary = "Get Employee By Id", description = "Get Employee By Id")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 Ok")
    @GetMapping(value = "/getById", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> getEmployeeById(@RequestParam("employeeId") Long employeeId) {

        return employeeService.getEmployeeById(employeeId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "update Employee By Id", description = "update Employee By Id")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @PutMapping(value = "/updateById", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> updateEmployeeById(@RequestParam("employeeId") Long employeeId, @RequestBody Employee updatedEmployee) {

        return employeeService.getEmployeeById(employeeId).map(employee -> {
            employee.setName(updatedEmployee.getName());
            employee.setAge(updatedEmployee.getAge());
            employee.setEmail(updatedEmployee.getEmail());
            return ResponseEntity.ok(employeeService.updateEmployee(employee));
        }).orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Delete Employee By Id", description = "Delete Employee By Id")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @DeleteMapping(value = "/deleteById", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deleteEmployeeById(@RequestParam("employeeId") Long employeeId) {

        return ResponseEntity.ok(Map.of("deleteCount", employeeService.deleteEmployeeById(employeeId)));
    }
}
