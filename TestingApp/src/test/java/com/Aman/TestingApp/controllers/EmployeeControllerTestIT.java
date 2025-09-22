package com.Aman.TestingApp.controllers;

import com.Aman.TestingApp.dto.EmployeeDto;
import com.Aman.TestingApp.entities.Employee;
import com.Aman.TestingApp.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

class EmployeeControllerTestIT extends AbstractIntegrationTest{

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee testEmployee;

    private EmployeeDto testEmployeeDto;

    @BeforeEach
    void setUp() {
        testEmployee = Employee.builder()
                .id(1L)
                .name("Aman Verma")
                .email("aman@gmail.com")
                .salary(102030L)
                .build();

        testEmployeeDto = EmployeeDto.builder()
                .id(1L)
                .name("Aman Verma")
                .email("aman@gmail.com")
                .salary(102030L)
                .build();

        employeeRepository.deleteAll();
    }

    @Test
    void testGetEmployeeById_success() {
        Employee savedEmployee = employeeRepository.save(testEmployee);
        webTestClient.get()
                .uri("/employees/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .isEqualTo(testEmployeeDto);

    }

    @Test
    void testGetEmployeeById_failure() {
        webTestClient.get()
                .uri("/employees/1")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void testCreateNewEmployee_whenEmployeeAlreadyExists_thenThrowException() {
        Employee savedEmployee = employeeRepository.save(testEmployee);
        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testCreateEmployee_whenEmployeeIsNotPresent_thenCreateNewEmployee() {
        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo(testEmployeeDto.getEmail())
                .jsonPath("$.name").isEqualTo(testEmployeeDto.getName());

    }

    // same way other controllers can be tested
}