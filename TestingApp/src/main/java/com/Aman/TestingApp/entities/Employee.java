package com.Aman.TestingApp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private Long salary;
}
