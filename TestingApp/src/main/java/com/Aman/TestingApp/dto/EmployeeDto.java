package com.Aman.TestingApp.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmployeeDto {
    private Long id;
    private String email;
    private String name;
    private Long salary;
}
