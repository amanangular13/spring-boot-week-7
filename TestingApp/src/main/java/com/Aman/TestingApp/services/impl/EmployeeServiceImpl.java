package com.Aman.TestingApp.services.impl;

import com.Aman.TestingApp.dto.EmployeeDto;
import com.Aman.TestingApp.entities.Employee;
import com.Aman.TestingApp.exceptions.ResourceNotFoundException;
import com.Aman.TestingApp.repositories.EmployeeRepository;
import com.Aman.TestingApp.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;


    @Override
    public EmployeeDto getEmployeeById(Long id) {
        log.info("Fetching employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {}", id);
                    throw new ResourceNotFoundException("Employee not found with id: " + id);
                });
        log.info("Successfully fetched employee with id: {}", id);
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto createNewEmployee(EmployeeDto employeeDto) {
        log.info("creating new employee with email : {}", employeeDto.getEmail());
        List<Employee> existingEmployee = employeeRepository.findByEmail(employeeDto.getEmail());

        if(!existingEmployee.isEmpty()) {
            log.error("Employee already exist with email: {}", employeeDto.getEmail());
            throw new RuntimeException("Employee already exist with email: " + employeeDto.getEmail());
        }

        Employee newEmployee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(newEmployee);
        log.info("Successfully created new employee with id: {}", savedEmployee.getId());
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        log.info("Updating employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {}", id);
                    throw new ResourceNotFoundException("Employee not found with id: " + id);
                });

        if(!employee.getEmail().equals(employeeDto.getEmail())) {
            log.error("Attempted to update email for employee with id: {}", id);
            throw new RuntimeException("The email of the employee cannot be updated");
        }

        employeeDto.setId(id);
        modelMapper.map(employeeDto, employee);

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Successfully updated employee with id: {}", id);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting the employee with id: {}", id);
        boolean exists = employeeRepository.existsById(id);
        if(!exists) {
            log.error("Employee not found with id: {}", id);
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
        log.info("Successfully deleted employee with id: {}", id);
    }
}
