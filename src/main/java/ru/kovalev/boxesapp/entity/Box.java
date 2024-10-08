package ru.kovalev.boxesapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "boxes")
public class Box {
    @Id
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String body;
    @Column(unique = true, nullable = false)
    private String marker;
}