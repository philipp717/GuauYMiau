package com.example.guauymiau.repository;

import com.example.guauymiau.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}