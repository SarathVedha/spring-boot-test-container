package com.vedha.repository;

import com.vedha.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, PagingAndSortingRepository<Employee, Long> {

    @Transactional // @Transactional required for custom query methods
    Optional<Employee> findEmployeeByEmail(String employeeEmail);

    @Transactional
    Long deleteEmployeeById(Long employeeId);

    @Transactional
    @Query("select e from Employee e where e.email = ?1 and e.name = ?2")
    Optional<Employee> findByJPQLIndexParam(String email, String name);

    @Transactional
    @Query("select e from Employee e where e.email = :email and e.name = :name")
    Optional<Employee> findByJPQLNamedParam(@Param("email") String email, @Param("name") String name);

    @Transactional
    @Query(value = "select * from employees e where e.email = ?1 and e.name = ?2", nativeQuery = true)
    Optional<Employee> findByJPQLNativeIndexParam(String email, String name);

    @Transactional
    @Query(value = "select * from employees e where e.email = :email and e.name = :name", nativeQuery = true)
    Optional<Employee> findByJPQLNativeNamedParam(@Param("email") String email, @Param("name") String name);

}
