package com.example.myapplication.data

import com.example.myapplication.data.local.PetDao
import com.example.myapplication.data.local.PetEntity
import com.example.myapplication.data.local.UserDao
import com.example.myapplication.data.local.UserEntity
import com.example.myapplication.model.Pet
import com.example.myapplication.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(private val userDao: UserDao, private val petDao: PetDao) {

    suspend fun registerUser(user: User): Boolean {
        return try {
            if (userDao.getUserByEmail(user.email) != null) {
                false
            } else {
                userDao.insertUser(UserEntity(user.email, user.password))
                true
            }
        } catch (e: Exception) {
            false
        }
    }

    suspend fun validateLogin(email: String, password: String): Boolean {
        val user = userDao.getUserByEmail(email)
        return user != null && user.password == password
    }

    suspend fun addPet(pet: Pet) {
        petDao.insertPet(PetEntity(name = pet.name, type = pet.type, ownerEmail = pet.ownerEmail))
    }

    suspend fun deletePet(pet: Pet) {
        // We need the ID for deletion in Room
        petDao.deletePet(PetEntity(id = pet.id, name = pet.name, type = pet.type, ownerEmail = pet.ownerEmail))
    }
    
    suspend fun updatePet(pet: Pet) {
         petDao.updatePet(PetEntity(id = pet.id, name = pet.name, type = pet.type, ownerEmail = pet.ownerEmail))
    }

    fun getPetsStream(email: String): Flow<List<Pet>> {
        return petDao.getPetsForUser(email).map { entities ->
            entities.map { Pet(it.id, it.name, it.type, it.ownerEmail) }
        }
    }
}
