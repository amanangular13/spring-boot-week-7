package com.Aman.TestingApp.services.impl;

import com.Aman.TestingApp.dto.EmployeeDto;
import com.Aman.TestingApp.entities.Employee;
import com.Aman.TestingApp.exceptions.ResourceNotFoundException;
import com.Aman.TestingApp.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee mockEmployee;
    private EmployeeDto mockEmployeeDto;

    @BeforeEach
    void setUp() {
        mockEmployee = Employee.builder()
                .id(1L)
                .name("Aman Verma")
                .email("aman@gmail.com")
                .salary(102030L)
                .build();

        mockEmployeeDto = modelMapper.map(mockEmployee, EmployeeDto.class);
    }

    @Test
    void testGetEmployeeById_WhenEmployeeIsPresent_ThenReturnEmployeeDto() {
        Long id = mockEmployee.getId();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee));

        EmployeeDto employeeDto = employeeService.getEmployeeById(id);

        assertThat(employeeDto.getId()).isEqualTo(id);
        assertThat(employeeDto.getName()).isEqualTo(mockEmployee.getName());
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployee.getEmail());
        assertThat(employeeDto.getSalary()).isEqualTo(mockEmployee.getSalary());

        verify(employeeRepository).findById(1L);
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEmployeeById_WhenEmployeeIsNotPresent_ThenThrowException() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.getEmployeeById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: 1");

        verify(employeeRepository).findById(1L);
    }

    @Test
    void testCreateNewEmployee_WhenValidEmployee_ThenCreateNewEmployee() {

        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);

        EmployeeDto employeeDto = employeeService.createNewEmployee(mockEmployeeDto);

        assertThat(employeeDto.getId()).isEqualTo(mockEmployeeDto.getId());
        assertThat(employeeDto.getName()).isEqualTo(mockEmployeeDto.getName());
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployeeDto.getEmail());
        assertThat(employeeDto.getSalary()).isEqualTo(mockEmployeeDto.getSalary());

        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);

        verify(employeeRepository, times(1)).findByEmail(mockEmployeeDto.getEmail());
        verify(employeeRepository, times(1)).save(employeeArgumentCaptor.capture());

        Employee capturedEmployee = employeeArgumentCaptor.getValue();
        assertThat(capturedEmployee.getEmail()).isEqualTo(mockEmployee.getEmail());
    }

    @Test
    void testCreateNewEmployee_WhenAttemptingToCreateNewEmployeeWithExistingEmail_ThenThrowRuntimeException() {
        when(employeeRepository.findByEmail(mockEmployeeDto.getEmail())).thenReturn(List.of(mockEmployee));

        assertThatThrownBy(() -> employeeService.createNewEmployee(mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee already exist with email: " + mockEmployee.getEmail());

        verify(employeeRepository).findByEmail(mockEmployeeDto.getEmail());
        verify(employeeRepository, never()).save(any());
    }

    // same way we can add unit test for rest of the functions in service
    // that is update and delete then the coverage will be 100%`
}