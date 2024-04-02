package com.vedha.service;

import com.vedha.entity.Employee;
import com.vedha.utill.SortField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployee();

    Optional<Employee> getEmployeeById(Long employeeId);

    Employee updateEmployee(Employee updatedEmployee);

    Long deleteEmployeeById(Long employeeId);

    void deleteEmployeeByIdNoReturn(Long employeeId);

    Page<Employee> getAllEmployeePaginated(int pageNumber, int pageSize, Sort.Direction sortDirection, SortField sortField);
}
