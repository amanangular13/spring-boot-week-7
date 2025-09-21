package com.Aman.TestingApp.repositories;

import com.Aman.TestingApp.entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .name("Aman Verma")
                .email("aman990011@gmail.com")
                .salary(30000L)
                .build();
    }

    @Test
    void testFindByEmail_whenEmailIsPresent_thenReturnEmployee() {
        employeeRepository.save(employee);

        List<Employee> employees = employeeRepository.findByEmail(employee.getEmail());

        assertThat(employees).isNotNull();
        assertThat(employees).isNotEmpty();
        assertThat(employees.getFirst()).isEqualTo(employee);
    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyList() {

        String randomEmail = "random123@test.com";

        List<Employee> employees = employeeRepository.findByEmail(randomEmail);

        assertThat(employees).isNotNull();
        assertThat(employees).isEmpty();
    }
}