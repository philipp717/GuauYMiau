package com.example.guauymiau.controller;

import com.example.guauymiau.model.Pet;
import com.example.guauymiau.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @GetMapping
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @PostMapping
    public Pet createPet(@RequestBody Pet pet) {
        return petRepository.save(pet);
    }

    @PutMapping("/{id}")
    public Pet updatePet(@PathVariable Long id, @RequestBody Pet petDetails) {
        return petRepository.findById(id)
                .map(pet -> {
                    pet.setName(petDetails.getName());
                    pet.setType(petDetails.getType());
                    pet.setOwnerEmail(petDetails.getOwnerEmail());
                    return petRepository.save(pet);
                })
                .orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable Long id) {
        petRepository.deleteById(id);
    }
}