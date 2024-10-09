package ru.kovalev.boxesapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kovalev.boxesapp.entity.Box;

import java.util.Optional;

public interface BoxesRepository extends JpaRepository<Box, Long> {

    Optional<Box> findByName(String name);

    Optional<Box> findByMarker(String marker);

    int deleteByName(String name);

    boolean existsByName(String name);
}
