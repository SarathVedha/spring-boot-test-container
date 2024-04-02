package com.vedha.service.impl;

import com.vedha.entity.Employee;
import com.vedha.exception.EmployeeException;
import com.vedha.repository.EmployeeRepository;
import com.vedha.service.EmployeeService;
import com.vedha.utill.SortField;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {

        boolean present = employeeRepository.findEmployeeByEmail(employee.getEmail()).isPresent();
        if (present) throw new EmployeeException("Employee Already Present: " + employee.getEmail());

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployee() { return employeeRepository.findAll(); }

    @Override
    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public Employee updateEmployee(Employee updatedEmployee) {

        return employeeRepository.save(updatedEmployee);
    }

    @Override
    public Long deleteEmployeeById(Long employeeId) {
        return employeeRepository.deleteEmployeeById(employeeId);
    }

    @Override
    public void deleteEmployeeByIdNoReturn(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public Page<Employee> getAllEmployeePaginated(int pageNumber, int pageSize, Sort.Direction sortDirection, SortField sortField) {

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField.getFiledValue()));
        return employeeRepository.findAll(pageRequest);
    }
}
