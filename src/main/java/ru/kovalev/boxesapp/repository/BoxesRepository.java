package ru.kovalev.boxesapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kovalev.boxesapp.entity.Box;

import java.util.Optional;

@Repository
public interface BoxesRepository extends JpaRepository<Box, String> {
    boolean update(Box box);
    Optional<Box> findByMarker(String marker);
}
