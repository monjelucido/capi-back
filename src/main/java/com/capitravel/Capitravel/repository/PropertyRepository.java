package com.capitravel.Capitravel.repository;

import com.capitravel.Capitravel.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    Optional<Property> findByName(String name);
}
