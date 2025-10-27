package com.example.myapplication.data

import java.util.UUID

data class Pet(val id: String = UUID.randomUUID().toString(), var name: String, var type: String)
data class User(val email: String, val password: String, val pets: MutableList<Pet> = mutableListOf())

object UserRepository {
    private val users = mutableListOf<User>()

    init {
        // Default user for testing
        val defaultPet = Pet(name = "Gandalf", type = "Gato")
        val defaultUser = User("usuario@duoc.cl", "Password123@", mutableListOf(defaultPet))
        users.add(defaultUser)
    }

    fun registerUser(email: String, password: String, petName: String, petType: String): Boolean {
        if (users.any { it.email == email }) {
            return false // User already exists
        }
        val newUser = User(email = email, password = password, pets = mutableListOf(Pet(name = petName, type = petType)))
        users.add(newUser)
        return true
    }

    fun validateLogin(email: String, password: String): Boolean {
        return users.any { it.email == email && it.password == password }
    }

    fun findUser(email: String): User? {
        return users.find { it.email == email }
    }

    fun addPet(email: String, petName: String, petType: String): Pet? {
        val user = findUser(email)
        return if (user != null) {
            val newPet = Pet(name = petName, type = petType)
            user.pets.add(newPet)
            newPet
        } else {
            null
        }
    }

    fun removePet(email: String, petId: String) {
        findUser(email)?.pets?.removeIf { it.id == petId }
    }
    
    fun getPets(email: String): List<Pet> {
        return findUser(email)?.pets ?: emptyList()
    }
}