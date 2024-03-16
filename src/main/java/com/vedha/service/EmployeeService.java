package com.vedha.service;

import com.vedha.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployee();

    Optional<Employee> getEmployeeById(Long employeeId);

    Employee updateEmployee(Employee updatedEmployee);

    Long deleteEmployeeById(Long employeeId);

    void deleteEmployeeByIdNoReturn(Long employeeId);
}
