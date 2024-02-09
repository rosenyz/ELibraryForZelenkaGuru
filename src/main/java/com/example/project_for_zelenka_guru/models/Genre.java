package com.example.project_for_zelenka_guru.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "genres")
@Data
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;
}
